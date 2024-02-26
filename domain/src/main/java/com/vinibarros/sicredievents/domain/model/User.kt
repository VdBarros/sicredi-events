package com.vinibarros.sicredievents.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
data class User(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    var email: String,
    var name: String
) : Serializable