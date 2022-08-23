package com.example.travelhelperapp.travelPlacesActivity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.widget.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.travelhelperapp.R
import com.example.travelhelperapp.adapter.TravelAdapter
import com.example.travelhelperapp.travelPlacesActivity.models.Data
import com.example.travelhelperapp.travelPlacesActivity.models.TravelPlace
import okhttp3.*
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.collections.ArrayList

class TravelPlaceActivity : AppCompatActivity() {

    private lateinit var searchButton: Button
    private lateinit var result: TextView
    private lateinit var searchBar: EditText
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: TravelAdapter
    private lateinit var travelPlaceList: List<Data>
    private lateinit var progressBarTravel: ProgressBar

    private val TAG: String = "TravelPlaceActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_travel_place)

        recyclerView = findViewById(R.id.recyclerViewId)
        recyclerView.layoutManager = LinearLayoutManager(this@TravelPlaceActivity)
        recyclerView.setHasFixedSize(true)

//        searchButton = findViewById(R.id.searchButton)
        searchBar = findViewById(R.id.searchBar)
        travelPlaceList = mutableListOf()

//        progressBarTravel = findViewById(R.id.progressBarTravel)

        searchBar.setOnKeyListener(object : View.OnKeyListener {
            override fun onKey(v: View?, keyCode: Int, event: KeyEvent): Boolean {
                if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                    val httpClient: OkHttpClient.Builder = OkHttpClient.Builder()
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
                    val call: Call<TravelPlace> = methods.getAllData(searchBar.text.toString())
                    call.enqueue(object : retrofit2.Callback<TravelPlace> {
                        override fun onResponse(
                            call: Call<TravelPlace>,
                            response: retrofit2.Response<TravelPlace>
                        ) {
                            Log.e(TAG, "onResponse: code: " + response.code())

                            val data: ArrayList<Data>? = response.body()?.data
                            travelPlaceList = response.body()?.data!!
                            adapter.setData(travelPlaceList)

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
                    return true
                }
                return false
            }
        })

        /*searchButton.setOnClickListener {

            val httpClient: OkHttpClient.Builder = OkHttpClient.Builder()
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
            val call: Call<TravelPlace> = methods.getAllData(searchBar.text.toString())
            call.enqueue(object : retrofit2.Callback<TravelPlace> {
                override fun onResponse(
                    call: Call<TravelPlace>,
                    response: retrofit2.Response<TravelPlace>
                ) {
                    Log.e(TAG, "onResponse: code: " + response.code())

                    val data: ArrayList<Data>? = response.body()?.data
                    travelPlaceList = response.body()?.data!!
                    adapter.setData(travelPlaceList)

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

        }*/

        adapter = TravelAdapter(applicationContext, travelPlaceList)
        recyclerView.adapter = adapter
        adapter.notifyDataSetChanged()

    }

}