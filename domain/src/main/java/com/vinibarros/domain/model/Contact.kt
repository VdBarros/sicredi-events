package com.vinibarros.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
data class Contact(
    var firstName: String? = null,
    var lastName: String? = null,
    var number: String? = null,
    var secondNumber: String? = null,
    var email: String? = null,
    var company: String? = null,
    var address: String? = null,
    var city: String? = null,
    var county: String? = null,
    var state: String? = null,
    var zip: String? = null,
    @PrimaryKey(autoGenerate = true) val id: Long = 0
): Serializable