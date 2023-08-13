package com.vinibarros.contacts.graph.module

import com.vinibarros.contacts.graph.scope.FragmentScope
import com.vinibarros.contacts.view.addContact.view.AddContactFragment
import com.vinibarros.contacts.view.contactList.view.ContactListFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
interface ContactsMainActivityModule {
    @FragmentScope
    @ContributesAndroidInjector
    fun contributeContactListFragment(): ContactListFragment

    @FragmentScope
    @ContributesAndroidInjector
    fun contributeAddContactFragment(): AddContactFragment

}
