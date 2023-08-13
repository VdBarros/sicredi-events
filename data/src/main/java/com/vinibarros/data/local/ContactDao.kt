package com.vinibarros.data.local

import androidx.lifecycle.LiveData
import androidx.room.*
import com.vinibarros.domain.model.Contact

@Dao
interface ContactDao {

    @Query("SELECT * FROM contact ORDER BY firstName ASC")
    fun getAllContacts(): List<Contact>?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertContact(contact: Contact): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertContactList(contactList: List<Contact>)

    @Delete
    fun deleteContact(contact: Contact)

    @Query("SELECT * from contact WHERE firstName OR lastName LIKE '%' || :query || '%'")
    fun findContactByName(query: String): List<Contact>

    @Query("SELECT * FROM contact WHERE id = :contactId")
    fun getContactById(contactId: Long): Contact?
}