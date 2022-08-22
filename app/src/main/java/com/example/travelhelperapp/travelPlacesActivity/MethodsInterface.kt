package com.example.travelhelperapp.travelPlacesActivity

import com.example.travelhelperapp.travelPlacesActivity.models.TravelPlace
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface MethodsInterface {

    @GET("locations/search")
    fun getAllData(@Query("query") name: String): Call<TravelPlace>
}