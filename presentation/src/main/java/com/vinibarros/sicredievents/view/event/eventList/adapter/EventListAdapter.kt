package com.vinibarros.sicredievents.view.event.eventList.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.vinibarros.sicredievents.domain.model.Event

class EventListAdapter(private val listener: EventListListener) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var items: List<Event> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return EventListViewHolder.inflate(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        items.getOrNull(position)?.let {
            (holder as EventListViewHolder).setupUI(it, listener)
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun setFilter(events: List<Event>) {
        items = events
        notifyDataSetChanged()
    }

    interface EventListListener {
        fun onEventClicked(eventId: String)
    }
}