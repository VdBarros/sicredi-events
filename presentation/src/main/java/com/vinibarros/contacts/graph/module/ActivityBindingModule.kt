package com.vinibarros.contacts.graph.module

import com.vinibarros.contacts.graph.scope.ActivityScope
import com.vinibarros.contacts.view.ContactsMainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
interface ActivityBindingModule {
    @ActivityScope
    @ContributesAndroidInjector(modules = [ContactsMainActivityModule::class])
    fun contributeContacts(): ContactsMainActivity

}