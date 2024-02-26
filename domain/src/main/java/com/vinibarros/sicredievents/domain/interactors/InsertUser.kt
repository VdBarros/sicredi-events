package com.vinibarros.sicredievents.domain.interactors

import com.vinibarros.sicredievents.domain.boundary.UserRepository
import com.vinibarros.sicredievents.domain.model.User
import javax.inject.Inject

class InsertUser @Inject constructor(private val userRepository: UserRepository) {
    suspend fun execute(user: User): Long? = userRepository.insertUser(user)
}
