package com.example.travelhelperapp.travelPlacesActivity

import com.example.travelhelperapp.travelPlacesActivity.models.TravelPlace
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface MethodsInterface {

//    @GET("api/users?page=2")
    @GET("locations/search?query=ohrid&limit=30&offset=0&units=km&location_id=1&currency=EUR&sort=relevance&lang=en_US")
    fun getAllData(): Call<TravelPlace>
}