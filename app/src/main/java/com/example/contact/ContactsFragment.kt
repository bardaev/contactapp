package com.example.contact

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.contact.database.Contact
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.contact_groups_fragment.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ContactsFragment: Fragment() {

    companion object {
        fun newInstance() = ContactsFragment()
    }

    private lateinit var viewModel: ContactsViewModel
    private lateinit var addOrEditContactViewModel: AddOrEditContactViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.contacts_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val mainActivity = activity as MainActivity
        viewModel = ViewModelProviders.of(mainActivity).get(ContactsViewModel::class.java)
        addOrEditContactViewModel = ViewModelProviders.of(mainActivity).get(AddOrEditContactViewModel::class.java)

        recycler.layoutManager = LinearLayoutManager(context)

        viewModel.launch(Dispatchers.Main) {
            viewModel.requestContacts().observe(viewLifecycleOwner, Observer<List<Contact>> {
                recycler.adapter = ContactsAdapter(it, mainActivity, addOrEditContactViewModel)
            })
        }

        activity?.fab?.apply {
            visibility = View.VISIBLE
            setOnClickListener {
                addOrEditContactViewModel.selectedImageUri = null
                addOrEditContactViewModel.existingContact = null
                addOrEditContactViewModel.groupId = viewModel.contactGroup?.group?.id!!
                val navController = mainActivity.navController
                navController.navigate(R.id.addContactFragment)
            }
        }
    }
}