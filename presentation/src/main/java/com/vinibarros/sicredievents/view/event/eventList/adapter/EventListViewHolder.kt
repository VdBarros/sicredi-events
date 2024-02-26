package com.vinibarros.sicredievents.view.event.eventList.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.squareup.picasso.Picasso
import com.vinibarros.sicredievents.R
import com.vinibarros.sicredievents.databinding.ListItemEventBinding
import com.vinibarros.sicredievents.domain.model.Event
import com.vinibarros.sicredievents.util.classes.CircleTransform
import com.vinibarros.sicredievents.util.extensions.getDate

class EventListViewHolder(private val binding: ListItemEventBinding) :
    androidx.recyclerview.widget.RecyclerView.ViewHolder(binding.root) {

    internal fun setupUI(item: Event, eventListListener: EventListAdapter.EventListListener) {
        binding.apply {
            event = item
            eventClickListener = eventListListener
            eventDate.text = item.date?.getDate()
            //Load only https (secure) images
            Picasso.get()
                .load(item.image)
                .placeholder(R.drawable.ic_logo_foreground)
                .transform(CircleTransform())
                .resize(80, 80)
                .centerCrop()
                .into(eventImage)
            executePendingBindings()
        }
    }

    companion object {
        fun inflate(parent: ViewGroup?) = EventListViewHolder(
            ListItemEventBinding.inflate(
                LayoutInflater.from(parent?.context),
                parent,
                false
            )
        )
    }
}