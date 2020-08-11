package com.example.contact

import android.app.Application
import android.net.Uri
import androidx.lifecycle.AndroidViewModel
import com.example.contact.database.AppDatabase
import com.example.contact.database.Contact
import com.example.contact.database.insertOrUpdate
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class AddOrEditContactViewModel(app: Application): AndroidViewModel(app), CoroutineScope {
    var selectedImageUri: Uri? = null
    private var database: AppDatabase = AppDatabase.getDatabase(app)
    var groupId: Int = 0
    var existingContact: Contact? = null
        set(value) {
            field = value
            value?.apply { groupId = value.groupId }
        }


    private var contactsDao = database.getContactsDao()
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    private val job: Job = Job()


    fun saveContact(contact: Contact) {
        launch(Dispatchers.IO) {
            if (selectedImageUri != null) {
                contact.photoUrl = selectedImageUri.toString()
            }
            contactsDao.insertOrUpdate(contact)
        }
    }

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }

    fun removeContact(item: Contact) {
        launch(Dispatchers.IO) {
            contactsDao.delete(item)
        }
    }
}