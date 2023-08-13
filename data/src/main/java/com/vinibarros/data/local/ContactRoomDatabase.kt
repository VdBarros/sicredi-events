package com.vinibarros.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.vinibarros.domain.model.Contact

@Database(entities = [Contact::class], version = 1, exportSchema = false)
abstract class ContactRoomDatabase : RoomDatabase() {

    abstract fun contactDao(): ContactDao
}