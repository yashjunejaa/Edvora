package com.example.myapplication

import android.content.Context
import android.graphics.BitmapFactory

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import java.net.URL

import java.util.*

class RidesAdapter(val context: Context, val ridess : List<Rides>) : RecyclerView.Adapter<RideViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RideViewHolder {
        val inflate : LayoutInflater = LayoutInflater.from(parent.context);
        val view : View = inflate.inflate(R.layout.item, parent, false)
        return RideViewHolder(view)
    }

    override fun onBindViewHolder(holder: RideViewHolder, position: Int) {

        holder.city.text = ridess[position].city
        holder.state.text = ridess[position].state
        holder.rideId.text = "Ride Id: " + ridess[position].rideId.toString()
        holder.date.text = "Date: " + (ridess[position].date)
        holder.distance.text = "Distance: " + (ridess[position].distance.toString())
        holder.original.text = "Origin Station: " + (ridess[position].originalStation.toString())
        holder.path.text = "Station Path: " + (Arrays.toString(ridess[position].stationPath))
        val url=ridess[position].url
        /*val bt = BitmapFactory.decodeStream(url.openConnection().getInputStream());
        holder.image.setImageBitmap(bt);*/
        Picasso.with(context).load(url).into(holder.image);
    }

    override fun getItemCount(): Int {
        return ridess.size
    }

}

class RideViewHolder(item : View) : RecyclerView.ViewHolder(item) {

    var image = item.findViewById<ImageView>(R.id.img);
    var city = item.findViewById<TextView>(R.id.city);
    var state = item.findViewById<TextView>(R.id.state);
    var rideId = item.findViewById<TextView>(R.id.ride_id);
    var date = item.findViewById<TextView>(R.id.date);
    var distance = item.findViewById<TextView>(R.id.distance);
    var original = item.findViewById<TextView>(R.id.original_station);
    var path = item.findViewById<TextView>(R.id.path);


}