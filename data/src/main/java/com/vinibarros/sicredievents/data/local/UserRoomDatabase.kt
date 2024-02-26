package com.vinibarros.sicredievents.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.vinibarros.sicredievents.domain.model.User

@Database(entities = [User::class], version = 1, exportSchema = false)
abstract class UserRoomDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao
}