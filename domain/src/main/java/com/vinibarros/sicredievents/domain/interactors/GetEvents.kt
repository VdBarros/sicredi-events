package com.vinibarros.sicredievents.domain.interactors

import com.vinibarros.sicredievents.domain.boundary.EventsRepository
import com.vinibarros.sicredievents.domain.model.Event
import io.reactivex.Single
import javax.inject.Inject

class GetEvents @Inject constructor(private val eventsRepository: EventsRepository) {
    suspend fun execute(): Single<List<Event>> = eventsRepository.getEvents()
}
