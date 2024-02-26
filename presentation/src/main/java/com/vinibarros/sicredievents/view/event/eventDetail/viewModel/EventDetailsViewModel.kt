package com.vinibarros.sicredievents.view.event.eventDetail.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.vinibarros.sicredievents.domain.interactors.CheckinEvent
import com.vinibarros.sicredievents.domain.interactors.GetCurrentUser
import com.vinibarros.sicredievents.domain.interactors.GetEventById
import com.vinibarros.sicredievents.domain.model.Event
import com.vinibarros.sicredievents.domain.model.User
import com.vinibarros.sicredievents.domain.util.provider.SchedulerProvider
import com.vinibarros.sicredievents.util.base.BaseViewModel
import com.vinibarros.sicredievents.util.classes.LiveEvent
import com.vinibarros.sicredievents.util.extensions.defaultScheduler
import kotlinx.coroutines.launch
import javax.inject.Inject

class EventDetailsViewModel @Inject constructor(
    private val schedulerProvider: SchedulerProvider,
    private val getEventById: GetEventById,
    private val checkinEvent: CheckinEvent,
    private val getCurrentUser: GetCurrentUser
) : BaseViewModel() {

    val event: LiveData<Event?> get() = _event
    val user: LiveData<User?> get() = _user


    private val _event by lazy { MutableLiveData<Event>() }
    private val _user by lazy { MutableLiveData<User?>() }

    init {
        initUser()
    }

    private fun initUser() = viewModelScope.launch {
        _user.postValue(getCurrentUser.execute())
    }

    fun getEvents(id: String) = viewModelScope.launch {
        loading.value = LiveEvent(true)
        getEventById.execute(id).defaultScheduler(schedulerProvider)
            .subscribe(::onEvent, ::onError).let(disposables::add)
    }

    fun checkinEvent() = viewModelScope.launch {
        loading.value = LiveEvent(true)
        _event.value?.let {
            checkinEvent.execute(it).defaultScheduler(schedulerProvider)
                .subscribe(::onEvent, ::onError).let(disposables::add)
        }
    }

    private fun onEvent(event: Event) {
        loading.value = LiveEvent(false)
        _event.postValue(event)
    }
}