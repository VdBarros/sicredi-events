package com.vinibarros.sicredievents.util.base

import androidx.annotation.CallSuper
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.ViewModel
import com.vinibarros.sicredievents.util.classes.LiveEvent
import com.vinibarros.sicredievents.util.classes.SingleLiveEvent
import io.reactivex.disposables.CompositeDisposable

abstract class BaseViewModel : ViewModel(), LifecycleObserver {

    val disposables = CompositeDisposable()

    val loading: SingleLiveEvent<LiveEvent<Boolean>> = SingleLiveEvent()
    val error: SingleLiveEvent<LiveEvent<Exception>> = SingleLiveEvent()


    fun onError(ex: Throwable) {
        loading.value = LiveEvent(false)
        error.value = LiveEvent(Exception(ex))
    }

    @CallSuper
    override fun onCleared() {
        disposables.clear()
    }
}