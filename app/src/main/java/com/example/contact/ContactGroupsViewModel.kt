package com.example.contact

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.contact.database.AppDatabase
import com.example.contact.database.ContactGroup
import com.example.contact.database.ContactGroupWithContacts
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class ContactGroupsViewModel(app: Application) : AndroidViewModel(app), CoroutineScope {
    var groups: MutableLiveData<List<ContactGroupWithContacts>> = MutableLiveData()
    private var database: AppDatabase = AppDatabase.getDatabase(app)

    private var groupsDao = database.getGroupsDao()
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    private  val job: Job = Job()


    fun requestGroups() {
        launch(Dispatchers.Main) {
            groups.value = withContext(Dispatchers.Default) {
                groupsDao.getAll()
            }
        }
    }


    fun saveGroup(group: ContactGroup) {
        launch(Dispatchers.Default) {
            groupsDao.insert(group)
        }
        requestGroups()
    }

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }
}
