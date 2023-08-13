package com.vinibarros.contacts.graph.module

import android.content.Context
import androidx.room.Room
import com.vinibarros.data.repository.DefaultContactsRepository
import com.vinibarros.contacts.util.DATABASE_NAME
import com.vinibarros.data.local.ContactDao
import com.vinibarros.data.local.ContactRoomDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object ApplicationProviderModule {

    @JvmStatic
    @Singleton
    @Provides
    fun provideContactDao(contactRoomDatabase: ContactRoomDatabase): ContactDao {
        return contactRoomDatabase.contactDao()
    }

    @JvmStatic
    @Singleton
    @Provides
    fun provideContactDatabase(context: Context): ContactRoomDatabase {
        return Room.databaseBuilder(context, ContactRoomDatabase::class.java, DATABASE_NAME).allowMainThreadQueries().build()
    }

    @JvmStatic
    @Singleton
    @Provides
    fun provideContactRepository(contactDao: ContactDao): DefaultContactsRepository {
        return DefaultContactsRepository(contactDao)
    }
}