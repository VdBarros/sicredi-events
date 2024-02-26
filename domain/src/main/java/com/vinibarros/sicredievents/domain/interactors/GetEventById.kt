package com.vinibarros.sicredievents.domain.interactors

import com.vinibarros.sicredievents.domain.boundary.EventsRepository
import com.vinibarros.sicredievents.domain.model.Event
import io.reactivex.Single
import javax.inject.Inject

class GetEventById @Inject constructor(private val eventsRepository: EventsRepository) {
    suspend fun execute(id: String): Single<Event> = eventsRepository.getEventById(id)
}
