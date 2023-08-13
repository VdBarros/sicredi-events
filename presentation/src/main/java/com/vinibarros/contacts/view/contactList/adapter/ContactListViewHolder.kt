package com.vinibarros.contacts.view.contactList.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.vinibarros.contacts.databinding.ListItemContactBinding
import com.vinibarros.domain.model.Contact

class ContactListViewHolder(private val binding: ListItemContactBinding) :
    androidx.recyclerview.widget.RecyclerView.ViewHolder(binding.root) {

    internal fun setupUI(item: Contact, contactListListener: ContactListAdapter.ContactListListener) {
        binding.apply {
            contact = item
            contactClickListener = contactListListener
            executePendingBindings()
        }
    }

    companion object {
        fun inflate(parent: ViewGroup?) = ContactListViewHolder(
            ListItemContactBinding.inflate(
                LayoutInflater.from(parent?.context),
                parent,
                false
            )
        )
    }
}