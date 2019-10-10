package com.shaoniiuc.theremembrance.helper

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds

object MapUtil {

     fun updateCameraPos(mMap : GoogleMap?, latlng: LatLng, zoom : Float = 12f) {
        val cameraPosition = CameraPosition.Builder().target(latlng).zoom(zoom)
            .build()
        mMap?.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))
    }

    fun updateMapBoundary(map:GoogleMap?, latlons:ArrayList<LatLng>) {
        val builder = LatLngBounds.builder()

        for (latLng in latlons) {
            builder.include(latLng)
        }
        val bounds = builder.build()

        try {
            val padding = 80
            map?.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, padding))
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}