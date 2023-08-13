package com.vinibarros.domain.interactors

import com.vinibarros.domain.boundary.ContactsRepository
import com.vinibarros.domain.model.Contact
import io.reactivex.Single
import javax.inject.Inject

class DeleteContact @Inject constructor(private val contactsRepository: ContactsRepository) {
    suspend fun execute(contact: Contact): Single<Contact?> = try {
        contactsRepository.deleteContact(contact)
        Single.just(contact)
    } catch (t: Throwable) {
        Single.error(t)
    }
}
