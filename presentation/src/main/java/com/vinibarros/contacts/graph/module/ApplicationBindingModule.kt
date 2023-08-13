package com.vinibarros.contacts.graph.module

import com.vinibarros.contacts.util.provider.DefaultSchedulerProvider
import com.vinibarros.data.repository.DefaultContactsRepository
import com.vinibarros.domain.boundary.ContactsRepository
import com.vinibarros.domain.util.provider.SchedulerProvider
import dagger.Binds
import dagger.Module

@Module
abstract class ApplicationBindingModule {

    @Binds
    abstract fun bindContactsRepository(
        impl: DefaultContactsRepository
    ): ContactsRepository

    @Binds
    abstract fun bindSchedulerProvider(
        impl: DefaultSchedulerProvider
    ): SchedulerProvider

}