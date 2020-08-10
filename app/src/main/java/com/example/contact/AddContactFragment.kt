package com.example.contact

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import com.example.contact.database.Contact
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.add_contact_fragment.*

class AddContactFragment: Fragment() {

    companion object {
        fun newInstance() = AddContactFragment()
        const val PICK_IMAGE = 1
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

        activity?.fab?.apply {
            visibility = View.GONE
        }

        imagePhoto.setOnClickListener {
            startActivityPickPhoto()
        }

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

    private fun startActivityPickPhoto() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_IMAGE && resultCode == Activity.RESULT_OK && data != null) {
            val selectedImageUri = data.data
            Picasso.get()
                .load(selectedImageUri)
                .resize(imagePhoto.maxWidth, imagePhoto.maxHeight)
                .centerCrop()
                .into(imagePhoto)
            viewModel.selectedImageUri = selectedImageUri
        }
    }
}