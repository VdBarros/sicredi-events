package com.vinibarros.sicredievents.data.repository

import com.vinibarros.sicredievents.data.local.UserDao
import com.vinibarros.sicredievents.domain.boundary.UserRepository
import com.vinibarros.sicredievents.domain.model.User
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DefaultUserRepository @Inject constructor(
    private val userDao: UserDao
) : UserRepository {

    override suspend fun getCurrentUser(): User? {
        return userDao.getCurrentUser()
    }

    override suspend fun insertUser(user: User): Long {
        return userDao.insertUser(user)
    }
}
