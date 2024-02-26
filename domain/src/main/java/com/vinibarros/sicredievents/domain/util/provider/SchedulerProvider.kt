package com.vinibarros.sicredievents.domain.util.provider


import io.reactivex.Scheduler

interface SchedulerProvider {
    fun main(): Scheduler
    fun io(): Scheduler
}