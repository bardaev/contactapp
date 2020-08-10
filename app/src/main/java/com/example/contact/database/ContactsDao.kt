package com.example.contact.database

import androidx.room.*

@Dao
interface ContactsDao {
    @Query("SELECT * FROM contacts WHERE group_id = :groupId")
    fun getContactsOfGroup(groupId: Int) : List<Contact>

    @Query("SELECT COUNT(id) FROM contacts WHERE group_id = :groupId")
    fun getCount(groupId: Int): Int

    @Insert
    fun insert(group: Contact)

    @Update
    fun update(group: Contact)

    @Delete
    fun delete(group: Contact)
}