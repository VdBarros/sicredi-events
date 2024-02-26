package com.vinibarros.sicredievents.domain.boundary

import com.vinibarros.sicredievents.domain.model.Event
import com.vinibarros.sicredievents.domain.model.User
import io.reactivex.Single

interface EventsRepository {
    suspend fun getEvents(): Single<List<Event>>
    suspend fun checkinEvent(event: Event, user: User): Single<Event>
    suspend fun getEventById(id: String): Single<Event>
}