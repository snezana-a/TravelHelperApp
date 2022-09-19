package com.example.travelhelperapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.travelhelperapp.R
import com.example.travelhelperapp.travelPlacesActivity.models.Data


class TravelAdapter(val applicationContext: Context, val data: List<Data>?, val recyclerViewInterface: RecyclerViewInterface) : RecyclerView.Adapter<TravelAdapter.MyViewHolder>() {

    private var travelPlaceList = emptyList<Data>()
    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TravelAdapter.MyViewHolder {
        val inflater = LayoutInflater.from(parent.context).inflate(R.layout.row_layout, parent, false)
        return MyViewHolder(inflater, recyclerViewInterface)
    }

    override fun onBindViewHolder(holder: TravelAdapter.MyViewHolder, position: Int) {
        holder.textViewName.text = travelPlaceList[position].result_object.name
        holder.textViewAddress.text = travelPlaceList[position].result_object.address
        holder.textViewRating.text = travelPlaceList[position].result_object.rating
        holder.itemView.setOnClickListener {
            val pos: Int = holder.adapterPosition

            if (pos != RecyclerView.NO_POSITION) {
                recyclerViewInterface.onItemClick(pos)
            }
        }
    }

    override fun getItemCount(): Int {
        return travelPlaceList.size
    }

    class MyViewHolder(val view: View, val recyclerViewInterface: RecyclerViewInterface): RecyclerView.ViewHolder(view) {
        var textViewName: TextView = view.findViewById(R.id.detailsName)
        var textViewAddress: TextView = view.findViewById(R.id.addressView)
        var textViewRating: TextView = view.findViewById(R.id.ratingView)
    }

    fun setData(newList: List<Data>) {
        travelPlaceList = newList
    }
}