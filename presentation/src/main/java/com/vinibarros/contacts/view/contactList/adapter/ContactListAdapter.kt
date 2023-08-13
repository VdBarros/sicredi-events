package com.vinibarros.contacts.view.contactList.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.vinibarros.domain.model.Contact
import java.util.ArrayList

class ContactListAdapter(private val listener: ContactListListener) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var items: List<Contact> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ContactListViewHolder.inflate(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        items.getOrNull(position)?.let {
            (holder as ContactListViewHolder).setupUI(it, listener)
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun setFilter(priceLists: List<Contact>) {
        items = priceLists as ArrayList<Contact>
        notifyDataSetChanged()
    }

    interface ContactListListener {
        fun onContactClicked(contact: Contact)
        fun onContactPhoneClicked(number: String?)
        fun onContactEmailClicked(email: String?)
    }
}