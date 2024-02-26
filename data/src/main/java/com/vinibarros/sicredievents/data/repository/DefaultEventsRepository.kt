package com.vinibarros.sicredievents.data.repository

import com.vinibarros.sicredievents.data.api.ApiClient
import com.vinibarros.sicredievents.domain.boundary.EventsRepository
import com.vinibarros.sicredievents.domain.model.Event
import com.vinibarros.sicredievents.domain.model.User
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DefaultEventsRepository @Inject constructor(
    private val apiClient: ApiClient
) : EventsRepository {

    override suspend fun getEvents(): Single<List<Event>> {
        return apiClient.getEvents()
    }

    override suspend fun checkinEvent(event: Event, user: User): Single<Event> {
        apiClient.checkin(event, user)
        return apiClient.getEvent(event.id)
    }

    override suspend fun getEventById(id: String): Single<Event> {
        return apiClient.getEvent(id)
    }
}
