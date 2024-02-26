package com.vinibarros.sicredievents.util.extensions

import com.vinibarros.sicredievents.domain.util.provider.SchedulerProvider
import io.reactivex.Single

fun <T> Single<T>.defaultScheduler(schedulerProvider: SchedulerProvider): Single<T> {
    return this.subscribeOn(schedulerProvider.io()).observeOn(schedulerProvider.main())
}