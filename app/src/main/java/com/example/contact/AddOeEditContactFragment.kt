package com.example.contact

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Build
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

class AddOrEditContactFragment : Fragment() {

    companion object {
        fun newInstance() = AddOrEditContactFragment()
        const val PICK_IMAGE = 1
    }

    private lateinit var viewModel: AddOrEditContactViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.add_contact_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val mainActivity = activity as MainActivity
        viewModel = ViewModelProviders.of(mainActivity).get(AddOrEditContactViewModel::class.java)
        if(viewModel.existingContact != null) {
            val contact = viewModel.existingContact!!

            editName.setText(contact.name)
            editPhone.setText(contact.phone)
            editComments.setText(contact.comment)

            editEmail.setText(contact.email)
            contact.photoUrl?.apply { imageWasSelected(Uri.parse(contact.photoUrl)) }


        }

        activity?.fab?.apply {
            visibility = View.GONE
        }

        imagePhoto.setOnClickListener {
            startActivityToPickPhoto()
        }

        buttonSubmit.setOnClickListener {
            val name = editName.text.toString()
            val phone = editPhone.text.toString()
            val email = editEmail.text.toString()

            val comment = editComments.text.toString()
            val existingContact = viewModel.existingContact

            val contact: Contact = if (existingContact != null) {

                existingContact.name = name
                existingContact.phone = phone
                existingContact.email = email
                existingContact.comment = comment
                existingContact

            } else {
                Contact(
                    name = name, phone = phone, email = email,
                    comment = comment, groupId = viewModel.groupId
                )
            }
            viewModel.saveContact(contact)
            it.findNavController().popBackStack()
        }
    }

    private fun startActivityToPickPhoto() {
        val intent = Intent()
        intent.type = "image/*"
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            intent.action = Intent.ACTION_OPEN_DOCUMENT
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
            intent.addFlags(Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION)
        } else {
            intent.action = Intent.ACTION_GET_CONTENT
        }
        intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true)
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_IMAGE && resultCode == Activity.RESULT_OK && data != null) {
            val selectedImageUri = data.data
            imageWasSelected(selectedImageUri)
        }
    }

    private fun imageWasSelected(selectedImageUri: Uri?) {
        Picasso.get()
            .load(selectedImageUri)
            .resize(imagePhoto.maxWidth, imagePhoto.maxHeight)
            .centerCrop()
            .into(imagePhoto)
        viewModel.selectedImageUri = selectedImageUri
    }
}
