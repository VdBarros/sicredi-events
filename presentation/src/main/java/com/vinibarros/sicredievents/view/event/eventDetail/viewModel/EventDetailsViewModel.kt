package com.vinibarros.sicredievents.view.event.eventDetail.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.vinibarros.sicredievents.domain.interactors.CheckinEvent
import com.vinibarros.sicredievents.domain.interactors.GetEventById
import com.vinibarros.sicredievents.domain.model.Event
import com.vinibarros.sicredievents.domain.util.provider.SchedulerProvider
import com.vinibarros.sicredievents.util.base.BaseViewModel
import com.vinibarros.sicredievents.util.extensions.defaultScheduler
import kotlinx.coroutines.launch
import javax.inject.Inject

class EventDetailsViewModel @Inject constructor(
    private val schedulerProvider: SchedulerProvider,
    private val getEventById: GetEventById,
    private val checkinEvent: CheckinEvent
) : BaseViewModel() {

    val event: LiveData<Event?> get() = _event

    private val _event by lazy { MutableLiveData<Event>() }

    fun getEvents(id: String) = viewModelScope.launch {
        getEventById.execute(id).defaultScheduler(schedulerProvider)
            .subscribe(::onEvent).let(disposables::add)
    }

    fun checkinEvent() = viewModelScope.launch {
        _event.value?.let {
            checkinEvent.execute(it).defaultScheduler(schedulerProvider)
                .subscribe(::onEvent).let(disposables::add)
        }
    }

    private fun onEvent(event: Event) {
        _event.postValue(event)
    }
}