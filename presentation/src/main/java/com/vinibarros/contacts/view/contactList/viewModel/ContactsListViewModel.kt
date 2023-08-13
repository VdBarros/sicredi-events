package com.vinibarros.contacts.view.contactList.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.vinibarros.contacts.util.base.BaseViewModel
import com.vinibarros.contacts.util.extensions.defaultScheduler
import com.vinibarros.domain.interactors.GetContacts
import com.vinibarros.domain.interactors.InsertContactList
import com.vinibarros.domain.model.Contact
import com.vinibarros.domain.util.provider.SchedulerProvider
import kotlinx.coroutines.launch
import javax.inject.Inject

class ContactsListViewModel @Inject constructor(
    private val schedulerProvider: SchedulerProvider,
    private val getContacts: GetContacts,
    private val insertContactList: InsertContactList
): BaseViewModel() {

    val contacts: LiveData<List<Contact>?> get() = _contacts

    private val _contacts by lazy { MutableLiveData<List<Contact>>() }

    fun getContacts() = viewModelScope.launch {
        getContacts.execute().defaultScheduler(schedulerProvider)
            .subscribe(::onContacts).let(disposables::add)
    }

    fun addContacts(contacts: List<Contact>) = viewModelScope.launch {
        insertContactList.execute(contacts).defaultScheduler(schedulerProvider)
            .subscribe(::onContacts).let(disposables::add)
    }

    private fun onContacts(contacts: List<Contact>) {
        _contacts.postValue(contacts)
    }
}