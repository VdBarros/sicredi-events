package com.vinibarros.sicredievents.graph.module

import com.vinibarros.sicredievents.data.repository.DefaultEventsRepository
import com.vinibarros.sicredievents.data.repository.DefaultUserRepository
import com.vinibarros.sicredievents.domain.boundary.EventsRepository
import com.vinibarros.sicredievents.domain.boundary.UserRepository
import com.vinibarros.sicredievents.domain.util.provider.SchedulerProvider
import com.vinibarros.sicredievents.util.provider.DefaultSchedulerProvider
import dagger.Binds
import dagger.Module

@Module
abstract class ApplicationBindingModule {

    @Binds
    abstract fun bindEventsRepository(
        impl: DefaultEventsRepository
    ): EventsRepository

    @Binds
    abstract fun bindUserRepository(
        impl: DefaultUserRepository
    ): UserRepository

    @Binds
    abstract fun bindSchedulerProvider(
        impl: DefaultSchedulerProvider
    ): SchedulerProvider

}