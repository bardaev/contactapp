package com.example.contact

import androidx.lifecycle.ViewModel
import com.example.contact.database.ContactGroup

class ContactGroupsViewModel: ViewModel() {
    fun getContactGroups(): ArrayList<ContactGroup> {
        return arrayListOf(
            ContactGroup("Parents", "My parents and family", R.color.yellow),
            ContactGroup("Work", "My colleagues and boss", R.color.blue),
            ContactGroup("Friends", "My friends and schoolmates", R.color.pink),
            ContactGroup("Someone I don't know", "Some people I met on the street", R.color.white),
            ContactGroup("Other people of planet Earth", "Yeah, in case I will need to add them", R.color.colorPrimary),
            ContactGroup("People from the Milky Way", "The galaxy is our common home", R.color.colorAccent)
        )
    }
}