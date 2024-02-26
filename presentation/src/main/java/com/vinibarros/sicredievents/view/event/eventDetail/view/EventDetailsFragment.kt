package com.vinibarros.sicredievents.view.event.eventDetail.view

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.squareup.picasso.Picasso
import com.vinibarros.sicredievents.R
import com.vinibarros.sicredievents.databinding.FragmentEventDetailsBinding
import com.vinibarros.sicredievents.domain.model.Event
import com.vinibarros.sicredievents.graph.scope.ViewModelProviderFactory
import com.vinibarros.sicredievents.util.extensions.errorObserver
import com.vinibarros.sicredievents.util.extensions.getDate
import com.vinibarros.sicredievents.util.extensions.loadingObserver
import com.vinibarros.sicredievents.util.extensions.toCurrency
import com.vinibarros.sicredievents.view.event.eventDetail.viewModel.EventDetailsViewModel
import dagger.android.support.DaggerFragment
import javax.inject.Inject


class EventDetailsFragment : DaggerFragment() {

    @Inject
    protected lateinit var viewModelProviderFactory: ViewModelProviderFactory<EventDetailsViewModel>

    private val viewModel: EventDetailsViewModel by viewModels(factoryProducer = ::viewModelProviderFactory)
    private lateinit var binding: FragmentEventDetailsBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEventDetailsBinding.inflate(inflater, container, false)
        subscribeUI()
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        arguments?.getString("eventId")?.let {
            viewModel.getEvents(it)
        }
    }


    private fun subscribeUI() {
        viewModel.event.observe(viewLifecycleOwner, ::onEvent)
        viewModel.error.observe(viewLifecycleOwner, errorObserver())
        viewModel.loading.observe(viewLifecycleOwner, loadingObserver(R.id.eventDetailsLoading))
    }

    private fun onEvent(event: Event?) {
        with(binding) {
            eventTitle.text = event?.title
            eventDescription.text = event?.description
            eventDate.text = event?.date?.getDate()
            eventPrice.text = event?.price?.toCurrency()
            buttonCheckin.setOnClickListener {
                viewModel.checkinEvent()
            }
            buttonOpenMap.setOnClickListener {
                openMap(event?.latitude, event?.longitude, event?.title)
            }

            buttonOpenMap.isEnabled = event?.people?.map {
                    user -> user.email
            }?.contains(viewModel.user.value?.email) == false
            Picasso
                .get()
                .load(event?.image)
                .placeholder(R.drawable.ic_logo_foreground)
                .into(eventImage)
        }
    }

    private fun openMap(lat: Double?, lng: Double?, title: String?) {
        try {
            val mapUri = Uri.parse("geo:0,0?q=$lat,$lng($title)")
            val mapIntent = Intent(Intent.ACTION_VIEW, mapUri)
            startActivity(mapIntent)
        } catch (_: Exception) {
            Toast.makeText(
                context,
                "Não foi possível encontrar o aplicativo de Mapa",
                Toast.LENGTH_LONG
            ).show()
        }
    }
}
