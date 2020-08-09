package com.example.contact

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.contact.database.AppDatabase
import com.example.contact.database.ContactGroup

class ContactGroupsViewModel(app: Application): AndroidViewModel(app) {
    var groups: MutableLiveData<List<ContactGroup>> = MutableLiveData()
    private var database = AppDatabase.getDatabase(app)
    private var groupsDao = database.getGroupsDao()

    fun insertTestInformation() {
        getContactGroups().forEach {
            groupsDao.insert(it)
        }
        groups.value = groupsDao.getAll()
    }
    fun getContactGroups(): ArrayList<ContactGroup> {
        return arrayListOf(
            ContactGroup(name = "Parents",description =  "My parents and family", color = R.color.yellow),
            ContactGroup(name = "Work", description = "My colleagues and boss", color = R.color.blue),
            ContactGroup(name = "Friends", description = "My friends and schoolmates", color = R.color.pink),
            ContactGroup(name = "Someone I don't know", description = "Some people I met on the street", color =  R.color.white),
            ContactGroup(name = "Other people of planet Earth", description = "Yeah, in case I will need to add them", color = R.color.colorPrimary),
            ContactGroup(name = "People from the Milky Way", description = "The galaxy is our common home", color = R.color.colorAccent)
        )
    }
}