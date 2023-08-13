package com.vinibarros.domain.interactors

import com.vinibarros.domain.boundary.ContactsRepository
import com.vinibarros.domain.model.Contact
import io.reactivex.Single
import javax.inject.Inject

class GetContacts @Inject constructor(private val contactsRepository: ContactsRepository) {
    suspend fun execute(): Single<List<Contact>> = try {
        Single.just(contactsRepository.getAllContacts().orEmpty())
    } catch (t: Throwable) {
        Single.error(t)
    }
}
