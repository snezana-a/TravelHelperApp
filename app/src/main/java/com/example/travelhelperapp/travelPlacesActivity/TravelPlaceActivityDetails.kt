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

class TravelPlaceActivityDetails : AppCompatActivity() {

    private lateinit var imageView: ImageView
    private lateinit var details: TextView
    private lateinit var image: String
    private lateinit var mapsButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_travel_place_details)

        imageView = findViewById(R.id.detailsImage)
        details = findViewById(R.id.detailsName)
        mapsButton = findViewById(R.id.mapsButton)

        val fragment: Fragment = MapFragment()

        image = intent.getStringExtra("IMAGE")!!
        Glide.with(applicationContext)
            .load(image)
            .placeholder(R.drawable.user)
            .error(R.drawable.aa)
            .into(imageView)

        details.text = intent.extras?.getString("NAME")

        mapsButton.setOnClickListener {
            val mapIntent = Intent(this@TravelPlaceActivityDetails, MapsActivity::class.java)

            mapIntent.putExtra("LAT", intent.getStringExtra("LAT"))
            mapIntent.putExtra("LNG", intent.getStringExtra("LNG"))
            mapIntent.putExtra("NAME", intent.getStringExtra("NAME"))

            startActivity(mapIntent)
        }

/*
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.frame_layout, fragment)
            .commit()*/

    }


}