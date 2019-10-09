package com.shaoniiuc.theremembrance.fragments


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.gms.common.api.Status
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.AutocompleteSupportFragment
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener

import com.shaoniiuc.theremembrance.R


class NewReminderFragment : Fragment(), OnMapReadyCallback, GoogleMap.OnMarkerClickListener {
    private val TAG = NewReminderFragment::class.java.simpleName
    private var mMap: GoogleMap? = null

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
                Place.Field.ADDRESS,
                Place.Field.NAME,
                Place.Field.LAT_LNG
            )
        )

        autocompleteFragment.setOnPlaceSelectedListener(object : PlaceSelectionListener {
            override fun onPlaceSelected(place: Place) {
                Log.i(TAG, "Place: " + place.name + ", " + place.latLng?.latitude)
                place.latLng?.let {
                    updateCameraPos(it)
                }
            }

            override fun onError(p0: Status) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
        })
    }

    private fun updateCameraPos(latlng: LatLng) {
        val cameraPosition = CameraPosition.Builder().target(latlng).zoom(12f)
            .build()
        mMap!!.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))
    }

    companion object {

        @JvmStatic
        fun newInstance() = NewReminderFragment()
    }


    override fun onMapReady(map: GoogleMap?) {
        map?.let {
            mMap = map
            map.setOnMarkerClickListener(this)
        }
    }

    override fun onMarkerClick(p0: Marker?) = true

}
