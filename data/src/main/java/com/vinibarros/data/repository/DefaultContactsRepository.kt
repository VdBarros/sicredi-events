package com.vinibarros.data.repository

import com.vinibarros.data.local.ContactDao
import com.vinibarros.domain.boundary.ContactsRepository
import com.vinibarros.domain.model.Contact
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DefaultContactsRepository @Inject constructor(private val contactDao: ContactDao) : ContactsRepository {

    override suspend fun getAllContacts() : List<Contact>? = contactDao.getAllContacts()

    override suspend fun insertContact(contact: Contact): Contact? {
        val newContactId = contactDao.insertContact(contact)
        return getContactById(newContactId)
    }

    override suspend fun insertContactList(contactList: List<Contact>): List<Contact>? {
        contactDao.insertContactList(contactList)
        return getAllContacts()
    }

    override suspend fun deleteContact(contact: Contact) = contactDao.deleteContact(contact)

    override suspend fun findContactByName(query: String): List<Contact> = contactDao.findContactByName(query)

    override suspend fun getContactById(id: Long): Contact? = contactDao.getContactById(id)
}
