package com.vinibarros.sicredievents.graph.module

import com.vinibarros.sicredievents.graph.scope.ActivityScope
import com.vinibarros.sicredievents.view.event.EventsMainActivity
import com.vinibarros.sicredievents.view.login.LoginMainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
interface ActivityBindingModule {
    @ActivityScope
    @ContributesAndroidInjector(modules = [EventsMainActivityModule::class])
    fun contributeContacts(): EventsMainActivity

    @ActivityScope
    @ContributesAndroidInjector
    fun contributeLogin(): LoginMainActivity
}