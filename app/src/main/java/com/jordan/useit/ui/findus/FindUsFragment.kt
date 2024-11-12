package com.jordan.useit.ui.findus

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.jordan.useit.databinding.FragmentFindUsBinding
import org.osmdroid.config.Configuration
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker

class FindUsFragment : Fragment() {

    private var _binding: FragmentFindUsBinding? = null
    private val binding get() = _binding!!

    private lateinit var map: MapView

    // Replace with your desired location coordinates
    private val locationGeoPoint = GeoPoint(-29.81283350141784, 30.643090909661414)

    // The URL to open when the marker is clicked
    private val mapsUrl = "https://maps.app.goo.gl/z8cpXMKPzZ6SnZMNA"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Load osmdroid configuration
        val ctx = activity?.applicationContext
        Configuration.getInstance().load(ctx, PreferenceManager.getDefaultSharedPreferences(ctx))
        Configuration.getInstance().userAgentValue = ctx?.packageName ?: "com.jordan.useit"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFindUsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        map = binding.mapView

        // Set multi-touch controls
        map.setMultiTouchControls(true)

        // Set the map zoom and center
        map.controller.setZoom(15.0)
        map.controller.setCenter(locationGeoPoint)

        // Add a marker at the location
        val startMarker = Marker(map)
        startMarker.position = locationGeoPoint
        startMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
        startMarker.title = "Use It Location"

        // Add click listener to the marker
        startMarker.setOnMarkerClickListener { _, _ ->
            openMapsLink()
            true
        }

        map.overlays.add(startMarker)
    }

    private fun openMapsLink() {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(mapsUrl))
        // Check if there's an app that can handle the intent
        if (intent.resolveActivity(requireActivity().packageManager) != null) {
            startActivity(intent)
        } else {
            // Handle the case where no app can handle the intent
            // You can show a message to the user or handle it accordingly
        }
    }

    override fun onResume() {
        super.onResume()
        map.onResume()
    }

    override fun onPause() {
        super.onPause()
        map.onPause()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
