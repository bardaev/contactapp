package com.example.contact.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "contacts")
class Contact (
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,
    @ColumnInfo(name = "group_id")
    var groupId: Int,
    var name: String,
    var email: String? = null,
    var phone: String? = null,
    var comment: String? = null,
    var photoUrl: String? = null
)