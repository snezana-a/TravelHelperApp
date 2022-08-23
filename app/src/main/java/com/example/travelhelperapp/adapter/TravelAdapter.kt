package com.example.travelhelperapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
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
        holder.textViewAddress.text = travelPlaceList[position].result_object.address
        holder.textViewRating.text = travelPlaceList[position].result_object.rating
//        holder.imageView.setImageResource(travelPlaceList[position].result_object.photo.images.thumbnail.url)
    }

    override fun getItemCount(): Int {
        return travelPlaceList.size
    }

    class MyViewHolder(view: View): RecyclerView.ViewHolder(view) {
        var textViewName: TextView = view.findViewById(R.id.titleView)
        var textViewAddress: TextView = view.findViewById(R.id.addressView)
        var textViewRating: TextView = view.findViewById(R.id.ratingView)
        var imageView: ImageView = view.findViewById(R.id.imageView)

    }

    fun setData(newList: List<Data>) {
        travelPlaceList = newList
    }
}