package com.example.contact

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import com.example.contact.database.Contact
import kotlinx.android.synthetic.main.add_contact_fragment.*

class AddContactFragment: Fragment() {

    companion object {
        fun newInstance() = AddContactFragment()
    }

    private lateinit var viewModel: AddContactViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.add_contact_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val mainActivity = activity as MainActivity
        viewModel = ViewModelProviders.of(mainActivity).get(AddContactViewModel::class.java)

        buttonSubmit.setOnClickListener {
            val name = editName.text.toString()
            val phone = editPhone.text.toString()
            val email = editEmail.text.toString()
            val comment = editComments.text.toString()

            val contact = Contact(
                name = name,
                phone = phone,
                email = email,
                comment = comment,
                groupId = viewModel.groupId
            )

            viewModel.saveContact(contact)
            it.findNavController().popBackStack()
        }
    }
}