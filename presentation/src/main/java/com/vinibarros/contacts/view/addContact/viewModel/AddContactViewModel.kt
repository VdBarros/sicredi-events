package com.vinibarros.contacts.view.addContact.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vinibarros.contacts.util.base.BaseViewModel
import com.vinibarros.contacts.util.extensions.defaultScheduler
import com.vinibarros.data.repository.DefaultContactsRepository
import com.vinibarros.domain.interactors.DeleteContact
import com.vinibarros.domain.interactors.InsertContact
import com.vinibarros.domain.model.Contact
import com.vinibarros.domain.util.provider.SchedulerProvider
import kotlinx.coroutines.launch
import javax.inject.Inject

class AddContactViewModel @Inject constructor(
    private val schedulerProvider: SchedulerProvider,
    private val insertContact: InsertContact,
    private val deleteContact: DeleteContact
): BaseViewModel()  {

    val contact: LiveData<Contact?> get() = _contact

    private val _contact by lazy { MutableLiveData<Contact?>() }

    private fun onContact(contact: Contact?) {
        _contact.postValue(contact)
    }

    fun saveContact(contact: Contact) = viewModelScope.launch {
        insertContact.execute(contact).defaultScheduler(schedulerProvider)
            .subscribe(::onContact).let(disposables::add)
    }

    fun deleteContact(contact: Contact) = viewModelScope.launch {
        deleteContact.execute(contact).defaultScheduler(schedulerProvider)
            .subscribe(::onContact).let(disposables::add)
    }

}