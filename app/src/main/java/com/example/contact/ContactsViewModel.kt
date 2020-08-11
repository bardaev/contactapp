package com.example.contact

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.contact.database.AppDatabase
import com.example.contact.database.Contact
import com.example.contact.database.ContactGroupWithContacts
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

class ContactsViewModel(app: Application): AndroidViewModel(app), CoroutineScope {
    var contactGroup: ContactGroupWithContacts? = null

    private var database: AppDatabase = AppDatabase.getDatabase(app)
    private var contactsDao = database.getContactsDao()
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    private val job: Job = Job()

    suspend fun requestContacts(): LiveData<List<Contact>> {
        return withContext(Dispatchers.Default) {
            contactsDao.getContactsOfGroup(contactGroup?.group?.id ?: 0)
        }
    }

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }
}