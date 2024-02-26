package com.vinibarros.sicredievents.view.event.eventList.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.vinibarros.sicredievents.R
import com.vinibarros.sicredievents.databinding.FragmentEventListBinding
import com.vinibarros.sicredievents.domain.model.Event
import com.vinibarros.sicredievents.graph.scope.ViewModelProviderFactory
import com.vinibarros.sicredievents.util.OPTIONS
import com.vinibarros.sicredievents.util.extensions.containsIgnoreCase
import com.vinibarros.sicredievents.util.extensions.errorObserver
import com.vinibarros.sicredievents.util.extensions.loadingObserver
import com.vinibarros.sicredievents.util.extensions.removeAccents
import com.vinibarros.sicredievents.util.extensions.safeNavigation
import com.vinibarros.sicredievents.view.event.eventList.adapter.EventListAdapter
import com.vinibarros.sicredievents.view.event.eventList.viewModel.EventListViewModel
import dagger.android.support.DaggerFragment
import javax.inject.Inject


class EventListFragment : SearchView.OnQueryTextListener, DaggerFragment(),
    EventListAdapter.EventListListener {

    private var adapter = EventListAdapter(this)
    private var searchView: SearchView? = null

    @Inject
    protected lateinit var viewModelProviderFactory: ViewModelProviderFactory<EventListViewModel>

    private val viewModel: EventListViewModel by viewModels(factoryProducer = ::viewModelProviderFactory)
    private lateinit var binding: FragmentEventListBinding
    private var events: List<Event> = emptyList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEventListBinding.inflate(inflater, container, false)
        setHasOptionsMenu(true)
        setupUi()
        subscribeUI()
        setupAdapter()
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        viewModel.getEvents()
    }

    override fun onEventClicked(eventId: String) {
        findNavController().safeNavigation(
            EventListFragmentDirections.eventListFragmentToEventDetailsFragment(
                eventId
            ),
            OPTIONS,
        )
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        inflater.inflate(R.menu.menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_search -> {
                resolveMenuSearchAction(item)
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        return false
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        if (!newText.isNullOrEmpty()) {
            adapter.setFilter(filter(events, newText.lowercase().removeAccents()))
        } else adapter.setFilter(events)
        return true
    }

    private fun filter(events: List<Event>, query: String): List<Event> {
        return events.filter { it.containsQuery(query) }
    }

    private fun Event.containsQuery(query: String): Boolean {
        return title.containsIgnoreCase(query) ||
                description.containsIgnoreCase(query)
    }

    private fun resolveMenuSearchAction(item: MenuItem) {
        searchView = (item.actionView as SearchView)
        searchView?.setOnQueryTextListener(this)
        item.setOnActionExpandListener(object : MenuItem.OnActionExpandListener {
            override fun onMenuItemActionCollapse(item: MenuItem): Boolean {
                adapter.setFilter(events)
                return true
            }

            override fun onMenuItemActionExpand(item: MenuItem): Boolean {
                return true
            }
        })
    }


    private fun subscribeUI() {
        viewModel.events.observe(viewLifecycleOwner, ::onEvents)
        viewModel.error.observe(viewLifecycleOwner, errorObserver {
            binding.eventListRefreshLayout.isRefreshing = false
        })
        viewModel.loading.observe(viewLifecycleOwner, loadingObserver(R.id.eventListLoading))
    }

    private fun onEvents(events: List<Event>?) {
        this.events = events.orEmpty()
        adapter.items = events.orEmpty()
        with(binding) {
            eventListRefreshLayout.isRefreshing = false
            eventList.isVisible = !events.isNullOrEmpty()
        }
    }

    private fun setupUi() {
        with(binding) {
            eventListRefreshLayout.setOnRefreshListener {
                searchView?.setQuery("", false)
                searchView?.clearFocus()
                viewModel.getEvents()
            }
        }
    }

    private fun setupAdapter() {
        binding.eventList.adapter = adapter
    }
}
