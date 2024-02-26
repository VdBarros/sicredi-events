package com.vinibarros.sicredievents.view.event.eventList.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.vinibarros.sicredievents.domain.interactors.GetEvents
import com.vinibarros.sicredievents.domain.model.Event
import com.vinibarros.sicredievents.domain.util.provider.SchedulerProvider
import com.vinibarros.sicredievents.util.base.BaseViewModel
import com.vinibarros.sicredievents.util.classes.LiveEvent
import com.vinibarros.sicredievents.util.extensions.defaultScheduler
import kotlinx.coroutines.launch
import javax.inject.Inject

class EventListViewModel @Inject constructor(
    private val schedulerProvider: SchedulerProvider,
    private val getEvents: GetEvents
) : BaseViewModel() {

    val events: LiveData<List<Event>?> get() = _events
    private val _events by lazy { MutableLiveData<List<Event>>() }

    fun getEvents() = viewModelScope.launch {
        loading.value = LiveEvent(true)
        getEvents.execute().defaultScheduler(schedulerProvider)
            .subscribe(::onEvents, ::onError).let(disposables::add)
    }

    private fun onEvents(events: List<Event>) {
        loading.value = LiveEvent(false)
        _events.postValue(events)
    }
}