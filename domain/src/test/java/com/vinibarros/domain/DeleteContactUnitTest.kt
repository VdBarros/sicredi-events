package com.vinibarros.domain

import com.vinibarros.domain.boundary.ContactsRepository
import com.vinibarros.domain.interactors.DeleteContact
import com.vinibarros.domain.interactors.InsertContact
import com.vinibarros.domain.model.Contact
import io.reactivex.observers.TestObserver
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito

@ExperimentalCoroutinesApi
class DeleteContactUnitTest {
    private lateinit var repo: ContactsRepository
    private lateinit var deleteContact: DeleteContact
    private lateinit var testObserver: TestObserver<Contact?>
    private lateinit var expectedContact: Contact
    private lateinit var currentContact: Contact

    @Before
    fun setup() = runTest  {
        repo = Mockito.mock(ContactsRepository::class.java)
        deleteContact = DeleteContact(repo)
        val contact = Contact(
            firstName = "James",
            lastName = "Butt",
            number = "504-621-8927",
            email = "504-845-1427",
            secondNumber = "70116",
            company = "Benton",
            address = "John B Jr",
            city = "6649 N Blue Gum St",
            county = "New Orleans",
            state = "Orleans",
            zip = "LA",
            id = 1,
        )
        expectedContact = contact
        currentContact = contact
        Mockito.doReturn(currentContact).`when`(repo).deleteContact(contact)
    }

    @Test
    fun execute() = runTest {
        testObserver = deleteContact.execute(expectedContact).test()
        testObserver.assertNoErrors()
        testObserver.assertComplete()
        testObserver.assertValue(expectedContact)
        Mockito.verify(repo).deleteContact(expectedContact)
    }
}