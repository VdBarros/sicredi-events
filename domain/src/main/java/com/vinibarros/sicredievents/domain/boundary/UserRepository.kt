package com.vinibarros.sicredievents.domain.boundary

import com.vinibarros.sicredievents.domain.model.User

interface UserRepository {
    suspend fun getCurrentUser(): User?
    suspend fun insertUser(user: User): Long?
}