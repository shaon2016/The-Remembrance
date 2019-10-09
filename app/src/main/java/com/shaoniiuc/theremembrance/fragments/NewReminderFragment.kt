package com.shaoniiuc.theremembrance.fragments


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.customview.customView
import com.afollestad.materialdialogs.customview.getCustomView
import com.google.android.gms.common.api.Status
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.AutocompleteSupportFragment
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener

import com.shaoniiuc.theremembrance.R


class NewReminderFragment : Fragment(), OnMapReadyCallback, GoogleMap.OnMarkerClickListener {


    private val TAG = NewReminderFragment::class.java.simpleName
    private var mMap: GoogleMap? = null
    private var marker: Marker? = null
    private var currentPlace : Place? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_new_reminder, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val mapFragment = childFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment!!.getMapAsync(this)

        // Initialize Places.
        Places.initialize(context!!, getString(R.string.google_maps_key))
        setPlaceAutoCompleteFragment()
    }

    private fun setPlaceAutoCompleteFragment() {
        val autocompleteFragment = childFragmentManager.findFragmentById(
            R.id.autocomplete_support_fragment
        ) as AutocompleteSupportFragment
        autocompleteFragment.setPlaceFields(
            listOf(
                Place.Field.ID,
                Place.Field.ADDRESS,
                Place.Field.NAME,
                Place.Field.LAT_LNG,
                Place.Field.OPENING_HOURS,
                Place.Field.PHONE_NUMBER,
                Place.Field.RATING,
                Place.Field.WEBSITE_URI
            )
        )


        autocompleteFragment.setOnPlaceSelectedListener(object : PlaceSelectionListener {
            override fun onPlaceSelected(place: Place) {
                Log.i(TAG, "Place: " + place.name + ", " + place.latLng?.latitude)
                place.latLng?.let {
                    currentPlace = place
                    addMarker(it, place.name ?: "")
                    updateCameraPos(it)
                }
            }

            override fun onError(st: Status) {
                Log.d(TAG, "Error getting place: $st")
            }
        })
    }

    private fun updateCameraPos(latlng: LatLng) {
        val cameraPosition = CameraPosition.Builder().target(latlng).zoom(12f)
            .build()
        mMap!!.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))
    }


    override fun onMapReady(map: GoogleMap?) {
        map?.let {
            mMap = map
            mMap?.setOnMarkerClickListener(this)

        }
    }

    private fun addMarker(mghLatLon: LatLng, title: String) {
        marker = mMap!!.addMarker(MarkerOptions().position(mghLatLon).title(title))
        marker!!.showInfoWindow()
    }

    override fun onMarkerClick(marker: Marker?): Boolean {
        marker?.let {
            if (this.marker != null && this.marker!! == marker) {
                showPlaceDetails()
            }
        }
        return true
    }

    private fun showPlaceDetails() {
        MaterialDialog(context!!).show {
            customView(R.layout.dialog_place_details)
            val v = getCustomView()

            val tvPlaceName = v.findViewById<TextView>(R.id.tvPlaceName)
            val tvPlaceAddress = v.findViewById<TextView>(R.id.tvPlaceAddress)
            val tvPlaceMobile = v.findViewById<TextView>(R.id.tvPlaceMobile)
            val tvPlaceRating = v.findViewById<TextView>(R.id.tvPlaceRating)
            val tvPlaceWebsite = v.findViewById<TextView>(R.id.tvPlaceWebsite)

            tvPlaceName.text = String.format("Mobile:  ${currentPlace?.name ?: "Not Available"}")
            tvPlaceAddress.text = String.format("Mobile:  ${currentPlace?.address ?: "Not Available"}")
            tvPlaceMobile.text = String.format("Mobile:  ${currentPlace?.phoneNumber ?: "Not Available"}")
            tvPlaceRating.text = String.format("Rating: ${currentPlace?.rating ?: "Not Available"}")
            tvPlaceWebsite.text = String.format("Web: ${currentPlace?.websiteUri ?: "Not Available"}")
        }
    }

    companion object {

        @JvmStatic
        fun newInstance() = NewReminderFragment()
    }

}
