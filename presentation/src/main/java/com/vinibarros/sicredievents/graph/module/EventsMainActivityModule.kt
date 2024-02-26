package com.vinibarros.sicredievents.graph.module

import com.vinibarros.sicredievents.graph.scope.FragmentScope
import com.vinibarros.sicredievents.view.event.eventDetail.view.EventDetailsFragment
import com.vinibarros.sicredievents.view.event.eventList.view.EventListFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
interface EventsMainActivityModule {
    @FragmentScope
    @ContributesAndroidInjector
    fun contributeEventListFragment(): EventListFragment

    @FragmentScope
    @ContributesAndroidInjector
    fun contributeEventDetailsFragment(): EventDetailsFragment
}
