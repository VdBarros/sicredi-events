package com.vinibarros.contacts.view.addContact.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.vinibarros.contacts.R
import com.vinibarros.contacts.databinding.FragmentAddContactBinding
import com.vinibarros.contacts.graph.scope.ViewModelProviderFactory
import com.vinibarros.contacts.util.OPTIONS
import com.vinibarros.contacts.util.extensions.isValidEmail
import com.vinibarros.contacts.util.extensions.safeNavigation
import com.vinibarros.contacts.view.addContact.viewModel.AddContactViewModel
import com.vinibarros.domain.model.Contact
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class AddContactFragment : DaggerFragment() {

    @Inject
    protected lateinit var viewModelProviderFactory: ViewModelProviderFactory<AddContactViewModel>

    private val viewModel: AddContactViewModel by viewModels(factoryProducer = ::viewModelProviderFactory)
    private lateinit var binding: FragmentAddContactBinding
    private var currentContact: Contact? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View {
        binding = FragmentAddContactBinding.inflate(inflater, container, false)
        currentContact = AddContactFragmentArgs.fromBundle(requireArguments()).contact
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribeUI()
        setupUi()
    }

    private fun subscribeUI() {
        viewModel.contact.observe(viewLifecycleOwner, ::onContact)
    }

    private fun onContact(contact: Contact?) {
        findNavController().safeNavigation(AddContactFragmentDirections
            .actionAddContactFragmentToContactsFragment())
    }

    private fun setupUi() {
        with(binding) {
            contact = currentContact
            executePendingBindings()

            editTextEmail.doAfterTextChanged {
                editTextEmail.error = null
            }

            buttonDelete.setOnClickListener {
                currentContact?.let { safeContact -> viewModel.deleteContact(safeContact) }
            }

            buttonSave.setOnClickListener {
                if ((editTextEmail.text.toString().isValidEmail() || editTextEmail.text.toString().isEmpty()) && !hasEmptyFields()) {
                    viewModel.saveContact(
                        Contact(
                            firstName = editTextFirstName.text.toString(),
                            lastName = editTextLastName.text.toString(),
                            number = editTextPhoneNumber.text.toString(),
                            email = editTextEmail.text.toString(),
                            secondNumber = editTextSecondPhoneNumber.text.toString(),
                            company = editTextCompanyName.text.toString(),
                            address = editTextAddress.text.toString(),
                            city = editTextCity.text.toString(),
                            county = editTextCounty.text.toString(),
                            state = editTextState.text.toString(),
                            zip = editTextZip.text.toString(),
                            id = currentContact?.id ?: 0
                        )
                    )
                } else editTextEmail.error = getString(R.string.error_email_invalid)
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this)
        {
            findNavController().safeNavigation(R.id.action_addContactFragment_to_contactsFragment, null, OPTIONS)
        }
    }

    private fun hasEmptyFields(): Boolean {
        with(binding) {
            return editTextFirstName.text.toString().isEmpty() &&
                    editTextLastName.text.toString().isEmpty() &&
                    editTextPhoneNumber.text.toString().isEmpty() &&
                    editTextEmail.text.toString().isEmpty() &&
                    editTextSecondPhoneNumber.text.toString().isEmpty() &&
                    editTextCompanyName.text.toString().isEmpty() &&
                    editTextAddress.text.toString().isEmpty() &&
                    editTextCity.text.toString().isEmpty() &&
                    editTextCounty.text.toString().isEmpty() &&
                    editTextState.text.toString().isEmpty() &&
                    editTextZip.text.toString().isEmpty()
        }
    }
}
