package com.vinibarros.domain

import com.vinibarros.sicredievents.domain.boundary.UserRepository
import com.vinibarros.sicredievents.domain.interactors.GetCurrentUser
import com.vinibarros.sicredievents.domain.model.User
import io.reactivex.observers.TestObserver
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito

@ExperimentalCoroutinesApi
class GetCurrentUserUnitTest {
    private lateinit var repo: UserRepository
    private lateinit var getCurrentUser: GetCurrentUser
    private lateinit var testObserver: TestObserver<User?>
    private lateinit var expectedUser: User
    private lateinit var currentUser: User

    @Before
    fun setup() = runTest {
        repo = Mockito.mock(UserRepository::class.java)
        getCurrentUser = GetCurrentUser(repo)
        val user = User(name = "Teste", email = "teste@gmail.com")
        expectedUser = user
        currentUser = user
        Mockito.doReturn(currentUser).`when`(repo).getCurrentUser()
    }

    @Test
    fun execute() = runTest {
        testObserver = io.reactivex.Single.just(getCurrentUser.execute()).test()
        testObserver.assertNoErrors()
        testObserver.assertValue(expectedUser)
        testObserver.assertComplete()
    }
}