package com.vinibarros.sicredievents.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.vinibarros.sicredievents.domain.model.User

@Dao
interface UserDao {

    @Query("SELECT * FROM user LIMIT 1")
    fun getCurrentUser(): User?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser(user: User): Long
}