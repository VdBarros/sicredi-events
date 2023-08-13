package com.vinibarros.domain

import com.vinibarros.domain.boundary.ContactsRepository
import com.vinibarros.domain.interactors.InsertContactList
import com.vinibarros.domain.model.Contact
import io.reactivex.observers.TestObserver
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito

@ExperimentalCoroutinesApi
class InsertContactListUnitTest {
    private lateinit var repo: ContactsRepository
    private lateinit var insertContactList: InsertContactList
    private lateinit var testObserver: TestObserver<List<Contact>>
    private lateinit var expectedContacts: List<Contact>
    private lateinit var currentContacts: List<Contact>

    @Before
    fun setup() = runTest  {
        repo = Mockito.mock(ContactsRepository::class.java)
        insertContactList = InsertContactList(repo)
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
        expectedContacts = listOf(contact)
        currentContacts = listOf(contact)
        Mockito.doReturn(currentContacts).`when`(repo).insertContactList(currentContacts)
    }

    @Test
    fun execute() = runTest {
        testObserver = insertContactList.execute(expectedContacts).test()
        testObserver.assertNoErrors()
        testObserver.assertComplete()
        testObserver.assertValue(expectedContacts)
        Mockito.verify(repo).insertContactList(expectedContacts)
    }
}