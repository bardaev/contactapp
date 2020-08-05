package com.example.contact

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.contact.database.ContactGroup
import kotlinx.android.synthetic.main.contact_group_card.view.*

class ContactGroupsAdapter(val items: ArrayList<ContactGroup>, val context: Context): RecyclerView.Adapter<GroupsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GroupsViewHolder {
        return GroupsViewHolder(LayoutInflater.from(context).inflate(R.layout.contact_group_card, parent, false))
    }

    override fun onBindViewHolder(holder: GroupsViewHolder, position: Int) {
        holder.titleTxt?.text = items[position].name
        holder.descriptionTxt?.text = items[position].description
        holder.card.setCardBackgroundColor(context.resources.getColor(items[position].color))
    }

    override fun getItemCount(): Int {
        return items.size
    }
}

class GroupsViewHolder(view: View): RecyclerView.ViewHolder(view) {
    val titleTxt = view.titleTxt
    val descriptionTxt = view.descriptionTxt
    val card = view.card
}