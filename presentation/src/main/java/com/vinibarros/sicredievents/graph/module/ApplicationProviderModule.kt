package com.vinibarros.sicredievents.graph.module

import android.content.Context
import androidx.room.Room
import com.vinibarros.sicredievents.data.api.ApiClient
import com.vinibarros.sicredievents.data.local.UserDao
import com.vinibarros.sicredievents.data.local.UserRoomDatabase
import com.vinibarros.sicredievents.data.repository.DefaultEventsRepository
import com.vinibarros.sicredievents.data.repository.DefaultUserRepository
import com.vinibarros.sicredievents.util.DATABASE_NAME
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object ApplicationProviderModule {

    @JvmStatic
    @Singleton
    @Provides
    fun provideUserDao(userRoomDatabase: UserRoomDatabase): UserDao {
        return userRoomDatabase.userDao()
    }

    @JvmStatic
    @Singleton
    @Provides
    fun provideContactDatabase(context: Context): UserRoomDatabase {
        return Room.databaseBuilder(context, UserRoomDatabase::class.java, DATABASE_NAME)
            .allowMainThreadQueries().build()
    }

    @JvmStatic
    @Singleton
    @Provides
    fun provideEventsRepository(apiClient: ApiClient): DefaultEventsRepository {
        return DefaultEventsRepository(apiClient)
    }

    @JvmStatic
    @Singleton
    @Provides
    fun provideUserRepository(userDao: UserDao): DefaultUserRepository {
        return DefaultUserRepository(userDao)
    }
}