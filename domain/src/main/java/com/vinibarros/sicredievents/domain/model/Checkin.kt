package com.vinibarros.sicredievents.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable


data class Checkin(
    val eventId: String,
    val email: String,
    val name: String
) : Serializable