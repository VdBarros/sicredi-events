package com.vinibarros.domain.interactors

import com.vinibarros.domain.boundary.ContactsRepository
import com.vinibarros.domain.model.Contact
import io.reactivex.Single
import javax.inject.Inject

class InsertContactList @Inject constructor(private val contactsRepository: ContactsRepository) {
    suspend fun execute(contacts: List<Contact>): Single<List<Contact>> = try {
        Single.just(contactsRepository.insertContactList(contacts))
    } catch (t: Throwable) {
        Single.error(t)
    }
}
