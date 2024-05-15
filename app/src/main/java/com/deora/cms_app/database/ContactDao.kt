package com.deora.cms_app.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ContactDao {
    @Insert(onConflict =OnConflictStrategy.REPLACE)
    suspend fun insert(contact: Contact):Long

    @Query("SELECT * FROM contacts")
    suspend fun getAllContacts():List<Contact>
    @Delete
    suspend fun delete(contact: Contact)
}