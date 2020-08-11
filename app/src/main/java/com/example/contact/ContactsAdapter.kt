package com.example.contact

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.contact.database.Contact
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.contact_card.view.*

class ContactsAdapter(
    private val items: List<Contact>,
    val context: Context,
    private val addOrEditContactViewModel: AddOrEditContactViewModel
) :
    RecyclerView.Adapter<ContactViewHolder>() {

    var FACEBOOK_URL = "https://www.facebook.com/"

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        return ContactViewHolder(
            LayoutInflater.from(context)
                .inflate(R.layout.contact_card, parent, false)
        )
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        val item = items[position]

        holder.nameText.text = item.name
        holder.emailText.text = item.email
        holder.phoneText.text = item.phone
        holder.commentText.text = item.comment

        if (item.photoUrl == null) {
            Picasso.get()
                .load(R.mipmap.ic_launcher)
                .centerCrop()
                .resize(holder.avatarImage.maxWidth, holder.avatarImage.maxHeight)
                .into(holder.avatarImage)
        } else {
            val uri = Uri.parse(item.photoUrl)
            Picasso.get()
                .load(uri)
                .placeholder(R.mipmap.ic_launcher)
                .centerCrop()
                .resize(holder.avatarImage.maxWidth, holder.avatarImage.maxHeight)
                .into(holder.avatarImage)
        }


        holder.menuIcon.setOnClickListener { v ->

            showPopupMenu(v, item)

        }

    }

    private fun showPopupMenu(v: View, item: Contact) {
        val popupMenu = PopupMenu(context, v)
        popupMenu.inflate(R.menu.contact_popup_menu)
        popupMenu.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.action_edit -> {
                    addOrEditContactViewModel.existingContact = item
                    val navController = v.findNavController()
                    navController.navigate(R.id.addContactFragment)
                    true
                }
                R.id.action_delete -> {
                    addOrEditContactViewModel.removeContact(item)
                    true
                }
                R.id.action_call -> {
                    val uri = "tel:${item.phone}"
                    val intent = Intent(Intent.ACTION_DIAL)

                    intent.data = Uri.parse(uri)
                    context.startActivity(intent)
                    true
                }

                else -> false
            }
        }
        popupMenu.show()
    }

}


class ContactViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val nameText = view.nameTxt
    val phoneText = view.phoneTxt
    val avatarImage = view.imageView
    val menuIcon = view.imageView
    val emailText = view.emailText
    val commentText = view.commentTxt
}