package com.vinibarros.contacts.view.contactList.view

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.widget.SearchView
import androidx.core.content.FileProvider
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.opencsv.CSVParserBuilder
import com.opencsv.CSVReaderBuilder
import com.opencsv.CSVWriter
import com.vinibarros.contacts.R
import com.vinibarros.contacts.databinding.FragmentContactListBinding
import com.vinibarros.contacts.graph.scope.ViewModelProviderFactory
import com.vinibarros.contacts.util.OPTIONS
import com.vinibarros.contacts.util.extensions.containsIgnoreCase
import com.vinibarros.contacts.util.extensions.removeAccents
import com.vinibarros.contacts.util.extensions.safeNavigation
import com.vinibarros.contacts.view.contactList.adapter.ContactListAdapter
import com.vinibarros.contacts.view.contactList.viewModel.ContactsListViewModel
import com.vinibarros.domain.model.Contact
import dagger.android.support.DaggerFragment
import java.io.File
import java.io.FileWriter
import java.io.InputStreamReader
import javax.inject.Inject


class ContactListFragment : SearchView.OnQueryTextListener, DaggerFragment(),
    ContactListAdapter.ContactListListener {

    private var adapter = ContactListAdapter(this)

    @Inject
    protected lateinit var viewModelProviderFactory: ViewModelProviderFactory<ContactsListViewModel>

    private val viewModel: ContactsListViewModel by viewModels(factoryProducer = ::viewModelProviderFactory)
    private lateinit var binding: FragmentContactListBinding
    private var contacts: List<Contact> = emptyList()

    private val startFileChooser =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                result.data?.data?.let { importCSV(it) }
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentContactListBinding.inflate(inflater, container, false)
        setHasOptionsMenu(true)
        subscribeUI()
        setupUI()
        setupAdapter()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getContacts()
    }

    override fun onContactClicked(contact: Contact) {
        findNavController().safeNavigation(
            ContactListFragmentDirections.actionContactListFragmentToAddContactFragment(
                contact
            )
        )
    }

    override fun onContactPhoneClicked(number: String?) {
        startActivity(Intent(Intent.ACTION_DIAL, Uri.parse("tel:$number")))
    }

    override fun onContactEmailClicked(email: String?) {
        val emailIntent = Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:$email"))
        startActivity(Intent.createChooser(emailIntent, getString(R.string.intent_email)))
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        inflater.inflate(R.menu.menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_search -> {
                resolveMenuSearchAction(item)
                true
            }

            R.id.action_import_csv -> {
                openFileChooser()
                true
            }

            R.id.action_export_csv -> {
                exportCSV()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        return false
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        if (!newText.isNullOrEmpty()) {
            adapter.setFilter(filter(contacts, newText.lowercase().removeAccents()))
        } else adapter.setFilter(contacts)
        return true
    }

    private fun filter(contacts: List<Contact>, query: String): List<Contact> {
        return contacts.filter { it.containsQuery(query) }
    }

    private fun Contact.containsQuery(query: String): Boolean {
        return firstName.containsIgnoreCase(query) ||
                lastName.containsIgnoreCase(query) ||
                number.containsIgnoreCase(query) ||
                email.containsIgnoreCase(query)
    }

    private fun resolveMenuSearchAction(item: MenuItem) {
        (item.actionView as SearchView).setOnQueryTextListener(this)
        item.setOnActionExpandListener(object : MenuItem.OnActionExpandListener {
            override fun onMenuItemActionCollapse(item: MenuItem): Boolean {
                adapter.setFilter(contacts)
                return true
            }

            override fun onMenuItemActionExpand(item: MenuItem): Boolean {
                return true
            }
        })
    }

    private fun openFileChooser() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.addCategory(Intent.CATEGORY_OPENABLE)
        intent.type = "*/*"
        startFileChooser.launch(Intent.createChooser(intent, getString(R.string.intent_open_csv)))
    }

    private fun importCSV(contentUri: Uri) {
        try {
            val inputStream = requireContext().contentResolver.openInputStream(contentUri)
            val reader = CSVReaderBuilder(InputStreamReader(inputStream))
                .withCSVParser(CSVParserBuilder().withSeparator(',').build())
                .build()
            val importedContacts = ArrayList<Contact>()
            var line: Array<String>?
            val headers: Array<String> = reader.readNext() ?: arrayOf()
            while (reader.readNext().also { line = it } != null) {
                val contact = createContactFromCSVLine(headers, line)
                importedContacts.add(contact)
            }
            inputStream?.close()
            viewModel.addContacts(importedContacts)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun createContactFromCSVLine(headers: Array<String>, csvLine: Array<String>?): Contact {
        val contactData = mutableMapOf<String, String?>()
        headers.forEachIndexed { index, header ->
            val value = csvLine?.getOrNull(index)
            contactData[header] = value
        }
        return Contact(
            firstName = contactData[FIRST_NAME],
            lastName = contactData[LAST_NAME],
            company = contactData[COMPANY],
            address = contactData[ADDRESS],
            city = contactData[CITY],
            county = contactData[COUNTY],
            state = contactData[STATE],
            zip = contactData[ZIP],
            number = contactData[NUMBER],
            secondNumber = contactData[SECOND_NUMBER],
            email = contactData[EMAIL]
        )
    }

    private fun exportCSV() {
        val outputFile = File(requireContext().getExternalFilesDir(null), OUTPUT_FILE_NAME)
        try {
            val csvWriter = CSVWriter(FileWriter(outputFile))

            val headers = arrayOf(
                FIRST_NAME,
                LAST_NAME,
                COMPANY,
                ADDRESS,
                CITY,
                COUNTY,
                STATE,
                ZIP,
                NUMBER,
                SECOND_NUMBER,
                EMAIL,
            )
            csvWriter.writeNext(headers)

            contacts.forEach { contact ->
                val rowData = arrayOf(
                    contact.firstName,
                    contact.lastName,
                    contact.company,
                    contact.address,
                    contact.city,
                    contact.county,
                    contact.state,
                    contact.zip,
                    contact.number,
                    contact.secondNumber,
                    contact.email
                )
                csvWriter.writeNext(rowData)
            }
            csvWriter.close()
            val shareIntent = Intent(Intent.ACTION_SEND).apply {
                type = "*/*"
                putExtra(
                    Intent.EXTRA_STREAM,
                    FileProvider.getUriForFile(requireContext(), PROVIDER, outputFile)
                )
                addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            }
            startActivity(Intent.createChooser(shareIntent, getString(R.string.intent_export_csv)))
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun subscribeUI() {
        viewModel.contacts.observe(viewLifecycleOwner, ::onContacts)
    }

    private fun onContacts(contacts: List<Contact>?) {
        this.contacts = contacts.orEmpty()
        adapter.items = contacts.orEmpty()
        with(binding) {
            buttonImportContacts.isVisible = contacts.isNullOrEmpty()
            contactList.isVisible = !contacts.isNullOrEmpty()
        }
    }

    private fun setupUI() {
        with(binding) {
            fab.setOnClickListener {
                it.findNavController().safeNavigation(
                    R.id.action_contactListFragment_to_addContactFragment,
                    null,
                    OPTIONS
                )
            }
            buttonImportContacts.setOnClickListener {
                openFileChooser()
            }
        }
    }

    private fun setupAdapter() {
        binding.contactList.adapter = adapter
    }

    companion object {
        const val FIRST_NAME = "first_name"
        const val LAST_NAME = "last_name"
        const val COMPANY = "company_name"
        const val ADDRESS = "address"
        const val CITY = "city"
        const val COUNTY = "county"
        const val STATE = "state"
        const val ZIP = "zip"
        const val NUMBER = "phone"
        const val EMAIL = "email"
        const val SECOND_NUMBER = "phone1"
        const val OUTPUT_FILE_NAME = "exported_contacts.csv"
        const val PROVIDER = "com.vinibarros.contacts.provider"
    }
}
