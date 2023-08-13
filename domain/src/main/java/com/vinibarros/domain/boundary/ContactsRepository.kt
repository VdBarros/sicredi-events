package com.vinibarros.domain.boundary

import com.vinibarros.domain.model.Contact
import io.reactivex.Single

interface ContactsRepository {
    suspend fun getAllContacts(): List<Contact>?
    suspend fun insertContact(contact: Contact): Contact?
    suspend fun insertContactList(contactList: List<Contact>): List<Contact>?
    suspend fun deleteContact(contact: Contact)
    suspend fun findContactByName(query: String): List<Contact>?
    suspend fun getContactById(id: Long): Contact?
}