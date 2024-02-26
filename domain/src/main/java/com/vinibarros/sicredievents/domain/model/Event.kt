package com.vinibarros.sicredievents.domain.model

import java.io.Serializable

data class Event(
    val date: Long? = null,
    val description: String? = null,
    val id: String,
    val image: String? = null,
    val latitude: Double? = null,
    val longitude: Double? = null,
    val people: List<User>,
    val price: Double? = null,
    val title: String? = null
) : Serializable