package com.example.travelhelperapp.travelPlacesActivity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.travelhelperapp.R
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class TravelPlaceActivityDetails : AppCompatActivity() {

    private lateinit var imageView: ImageView
    private lateinit var details: TextView
    private lateinit var image: String
    lateinit var googleMap: GoogleMap
    lateinit var mapFragment: SupportMapFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_travel_place_details)

        imageView = findViewById(R.id.detailsImage)
        details = findViewById(R.id.detailsName)

        image = intent.getStringExtra("IMAGE")!!
        Glide.with(applicationContext)
            .load(image)
            .placeholder(R.drawable.user)
            .error(R.drawable.aa)
            .into(imageView)

        details.text = intent.extras?.getString("NAME")

        mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(OnMapReadyCallback {
            googleMap = it

            val location1 = LatLng(intent.getStringExtra("LAT")!!.toDouble(), intent.getStringExtra("LNG")!!.toDouble())
            googleMap.addMarker(MarkerOptions().position(location1).title("Object Location"))
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(location1, 10f))
        })
    }
}