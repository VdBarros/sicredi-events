package com.vinibarros.sicredievents.domain.interactors

import com.vinibarros.sicredievents.domain.boundary.EventsRepository
import com.vinibarros.sicredievents.domain.boundary.UserRepository
import com.vinibarros.sicredievents.domain.model.Event
import io.reactivex.Single
import javax.inject.Inject

class CheckinEvent @Inject constructor(
    private val eventsRepository: EventsRepository,
    private val userRepository: UserRepository
) {
    suspend fun execute(event: Event): Single<Event> {
        userRepository.getCurrentUser()?.let {
            return eventsRepository.checkinEvent(event, it)
        } ?: run {
            throw Exception("Nenhum usuário encontrado")
        }
    }
}
