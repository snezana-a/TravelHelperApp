package com.example.travelhelperapp.travelPlacesActivity

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.travelhelperapp.R
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MapFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view: View = layoutInflater.inflate(R.layout.activity_maps, container, false)

        val supportMapFragment: SupportMapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment


        /*val supportMapFragment: SupportMapFragment = childFragmentManager.findFragmentById(R.id.google_map) as SupportMapFragment

        supportMapFragment.getMapAsync { object : OnMapReadyCallback {
            override fun onMapReady(googleMap: GoogleMap) {
                googleMap.setOnMapClickListener { object : GoogleMap.OnMapClickListener{
                    override fun onMapClick(latLng: LatLng) {
                        val markerOptions = MarkerOptions()
                        markerOptions.position(latLng)
                        markerOptions.title(latLng.latitude.toString() + " : " + latLng.longitude.toString())
                        googleMap.clear()
                        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10F))
                        googleMap.addMarker(markerOptions)
                    }
                } }
            }
        } }*/

        return view
    }
}