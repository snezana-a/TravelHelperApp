package com.example.travelhelperapp.travelPlacesActivity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.travelhelperapp.R

class TravelPlaceActivityDetails : AppCompatActivity() {

    private lateinit var imageView: ImageView
    private lateinit var details: TextView
//    private var image: Int = 0
    private lateinit var image: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_travel_place_details)

        imageView = findViewById(R.id.detailsImage)
        details = findViewById(R.id.detailsName)

//        image = intent.extras?.getInt("IMAGE") ?: 0
//        var img = intent.extras?.get("IMAGE")
        image = intent.getStringExtra("IMAGE")!!
        Glide.with(applicationContext)
            .load(image)
            .placeholder(R.drawable.user)
            .error(R.drawable.aa)
            .into(imageView)

        details.text = intent.extras?.getString("NAME")
//        imageView.setImageResource(image)

    }


}