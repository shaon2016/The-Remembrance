package com.shaoniiuc.theremembrance.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.customview.customView
import com.afollestad.materialdialogs.customview.getCustomView
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.shaoniiuc.theremembrance.R
import com.shaoniiuc.theremembrance.helper.MapUtil
import com.shaoniiuc.theremembrance.helper.obtainViewModel
import com.shaoniiuc.theremembrance.models.Task
import com.shaoniiuc.theremembrance.viewmodels.RemindersMapVM
import kotlinx.android.synthetic.main.my_toolbar.*

class RemindersMapActivity : AppCompatActivity(), OnMapReadyCallback, GoogleMap.OnMarkerClickListener  {

    private lateinit var remindersMapVM: RemindersMapVM
    private var mMap: GoogleMap? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reminders_map)

        initVar()
        initView()
    }

    private fun initVar() {
        remindersMapVM = obtainViewModel(RemindersMapVM::class.java)
    }

    private fun initView() {
        configureToolbar()

        initMap()
        remindersMapVM.tasksLive.observe(this, Observer { tasks ->
            if (!tasks.isNullOrEmpty()) {
                val latllngs = ArrayList<LatLng>()
                tasks.forEach { t ->
                    latllngs.add(LatLng(t.lat, t.lon))
                    addMarkerToMap(t)
                }
                MapUtil.updateMapBoundary(mMap, latllngs)
            }
        })
    }

    private fun initMap() {
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(this)

    }

    private fun addMarkerToMap(t: Task) {
        val marker = mMap?.addMarker(MarkerOptions().position(LatLng(t.lat, t.lon)).title(t.placeName))
        marker?.tag = t
        marker?.showInfoWindow()
    }

    private fun configureToolbar() {
        setSupportActionBar(toolbar)
        val actionbar = supportActionBar
        actionbar?.setHomeAsUpIndicator(R.drawable.ic_keyboard_backspace_white_24dp)
        actionbar?.setDisplayHomeAsUpEnabled(true)
        actionbar?.title = getString(R.string.all_reminders)
        toolbar?.setTitleTextColor(ContextCompat.getColor(this, R.color.white))
        toolbar?.setNavigationOnClickListener { v -> onBackPressed() }
    }

    override fun onMapReady(map: GoogleMap?) {
        map?.let {
            mMap = map
            mMap?.setOnMarkerClickListener(this)
        }
    }

    override fun onMarkerClick(marker: Marker?): Boolean {
        marker?.let {
            showPlaceDetails(marker)
        }
        return true
    }

    private fun showPlaceDetails(marker: Marker) {
        MaterialDialog(this).show {
            customView(R.layout.dialog_place_details)
            val v = getCustomView()

            val tvPlaceName = v.findViewById<TextView>(R.id.tvPlaceName)
            val tvPlaceAddress = v.findViewById<TextView>(R.id.tvPlaceAddress)
            val tvPlaceMobile = v.findViewById<TextView>(R.id.tvPlaceMobile)
            val tvPlaceRating = v.findViewById<TextView>(R.id.tvPlaceRating)
            val tvPlaceWebsite = v.findViewById<TextView>(R.id.tvPlaceWebsite)

            val t = marker.tag as Task
            tvPlaceName.text = t.placeName
            tvPlaceAddress.text = t.placeAddress
            tvPlaceMobile.text = t.placeMobile
            tvPlaceRating.text = t.placeRating
            tvPlaceWebsite.text = t.placeWeb
        }
    }
}
