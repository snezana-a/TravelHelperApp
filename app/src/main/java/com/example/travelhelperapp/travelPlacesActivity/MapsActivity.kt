package com.example.travelhelperapp.travelPlacesActivity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.travelhelperapp.R
import com.example.travelhelperapp.databinding.ActivityMapsBinding

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        /*supportFragmentManager
            .beginTransaction()
            .replace(R.id.frame_layout, mapFragment)
            .commit()*/
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Add a marker in Sydney and move the camera
        val locationMarker = LatLng(intent.getStringExtra("LAT")!!.toDouble(), intent.getStringExtra("LNG")!!.toDouble())
        mMap.addMarker(MarkerOptions().position(locationMarker).title("Marker in " + intent.getStringExtra("NAME")))
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(locationMarker, 15F))
    }
}