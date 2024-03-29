package com.vinibarros.sicredievents.data.api

import com.vinibarros.sicredievents.data.util.request.RequestHandler
import com.vinibarros.sicredievents.domain.model.Checkin
import com.vinibarros.sicredievents.domain.model.Event
import com.vinibarros.sicredievents.domain.model.User
import io.reactivex.Single
import javax.inject.Inject

class ApiClient @Inject constructor(
    private val apiService: ApiService
) : RequestHandler() {

    fun getEvents(): Single<List<Event>> {
        return makeRequest(apiService.getEvents())
    }

    fun getEvent(eventId: String): Single<Event> {
        return makeRequest(apiService.getEvent(eventId))
    }

    fun checkin(event: Event, user: User): Single<Void> {
        return makeRequest(apiService.checkin(Checkin(event.id, user.email, user.name)))
    }
}