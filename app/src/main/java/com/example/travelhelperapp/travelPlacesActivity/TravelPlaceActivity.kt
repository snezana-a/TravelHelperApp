package com.example.travelhelperapp.travelPlacesActivity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.*
import com.example.travelhelperapp.R
import com.example.travelhelperapp.travelPlacesActivity.models.Data
import com.example.travelhelperapp.travelPlacesActivity.models.TravelPlace
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.ArrayList

class TravelPlaceActivity : AppCompatActivity() {

    private lateinit var searchButton: Button
    private lateinit var result: TextView
    private val TAG: String = "TravelPlaceActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_travel_place)

        searchButton = findViewById(R.id.searchButton)
        result = findViewById(R.id.result)

        searchButton.setOnClickListener {
            val httpClient: OkHttpClient.Builder = OkHttpClient.Builder()
            /*val mediaType: MediaType? = "application/json".toMediaTypeOrNull()
            val body: RequestBody = RequestBody.create(mediaType, "{\r" +
                    "\"query\": \"skopje\",\r" +
                    "\"updateToken\": \"\"\r}")*/
            httpClient.addInterceptor(Interceptor { chain ->
                val original: Request = chain.request()

                val request: Request = original.newBuilder()
                    .header("User-Agent", "TravelHelperApp")
                    .header("Accept", "application/json")
                    .addHeader("X-RapidAPI-Key", "13ae0b47a7mshaa6561744f418acp1d057cjsn653531d92414")
                    .addHeader("X-RapidAPI-Host", "travel-advisor.p.rapidapi.com")
                    .method(original.method, original.body)
                    .build()
                chain.proceed(request)
            })

            val client: OkHttpClient = httpClient.build()
            val retrofit: Retrofit = Retrofit.Builder()
                .baseUrl("https://travel-advisor.p.rapidapi.com/ ")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()

        val methods: MethodsInterface = retrofit.create(MethodsInterface::class.java)
            val call: Call<TravelPlace> = methods.getAllData()
            call.enqueue(object : retrofit2.Callback<TravelPlace> {
                override fun onResponse(
                    call: Call<TravelPlace>,
                    response: retrofit2.Response<TravelPlace>
                ) {
                    Log.e(TAG, "onResponse: code: " + response.code())

                    val data: ArrayList<Data>? = response.body()?.data

                    if (data != null) {
                        for (data1: Data in data) {
                            Log.e(TAG, "onResponse: name : " + data1.result_object.name);
                        }
                    }
                }

                override fun onFailure(call: Call<TravelPlace>, t: Throwable) {
                    Log.e(TAG, "onFailure: " + t.message )
                }

            })
        }
    }


}