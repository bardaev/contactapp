package com.example.contact.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.contact.R

@Entity(tableName = "groups")
class ContactGroup(
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,
    var name: String,
    var description: String? = null,
    var color: Int = R.color.colorPrimary
) {
}