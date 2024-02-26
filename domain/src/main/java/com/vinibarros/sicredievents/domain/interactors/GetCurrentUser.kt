package com.vinibarros.sicredievents.domain.interactors

import com.vinibarros.sicredievents.domain.boundary.UserRepository
import com.vinibarros.sicredievents.domain.model.User
import javax.inject.Inject

class GetCurrentUser @Inject constructor(private val userRepository: UserRepository) {
    suspend fun execute(): User? = userRepository.getCurrentUser()
}
