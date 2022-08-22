package com.example.travelhelperapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.travelhelperapp.R
import com.example.travelhelperapp.travelPlacesActivity.models.Data


class TravelAdapter(applicationContext: Context, data: List<Data>?) : RecyclerView.Adapter<TravelAdapter.MyViewHolder>() {

    private var travelPlaceList = emptyList<Data>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TravelAdapter.MyViewHolder {
        val inflater = LayoutInflater.from(parent.context).inflate(R.layout.row_layout, parent, false)
        return MyViewHolder(inflater)
    }

    override fun onBindViewHolder(holder: TravelAdapter.MyViewHolder, position: Int) {
        holder.textViewName.text = travelPlaceList[position].result_object.name
        holder.textViewDesc.text = travelPlaceList[position].result_object.description
        holder.textViewRating.text = travelPlaceList[position].result_object.rating
    }

    override fun getItemCount(): Int {
        return travelPlaceList.size
    }

    class MyViewHolder(view: View): RecyclerView.ViewHolder(view) {
        var textViewName: TextView = view.findViewById(R.id.textViewName)
        var textViewDesc: TextView = view.findViewById(R.id.textViewDesc)
        var textViewRating: TextView = view.findViewById(R.id.textViewRating)

    }

    fun setData(newList: List<Data>) {
        travelPlaceList = newList
    }
}