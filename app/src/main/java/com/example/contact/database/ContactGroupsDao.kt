package com.example.contact.database

import androidx.room.*

@Dao
interface ContactGroupsDao {

    @Query("SELECT * FROM groups")
    fun getAll(): List<ContactGroup>

    @Insert
    fun insert(group: ContactGroup)

    @Update
    fun update(group: ContactGroup)

    @Delete
    fun delete(group: ContactGroup)
}