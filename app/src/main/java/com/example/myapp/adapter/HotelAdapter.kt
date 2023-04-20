package com.example.myapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapp.Hotel
import com.example.myapp.R // Đảm bảo đã import R cho đúng package

class HotelAdapter(private var hotels: List<Hotel>) : RecyclerView.Adapter<HotelAdapter.HotelViewHolder>() {

    // Lớp HotelViewHolder
    inner class HotelViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageViewHotel = itemView.findViewById<ImageView>(R.id.imageViewHotel)
        val textViewHotelName = itemView.findViewById<TextView>(R.id.textViewHotelName)
        val textViewHotelAddress = itemView.findViewById<TextView>(R.id.textViewHotelAddress)
        val textViewHotelPrice = itemView.findViewById<TextView>(R.id.textViewHotelPrice)
        val ratingBarHotelRating = itemView.findViewById<RatingBar>(R.id.ratingBarHotelRating)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HotelViewHolder {
        // Inflate layout của item trong danh sách
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_hotel, parent, false)
        return HotelViewHolder(view)
    }

    override fun onBindViewHolder(holder: HotelViewHolder, position: Int) {
        // Thiết lập dữ liệu cho từng item trong danh sách
        val hotel = hotels[position]
        holder.imageViewHotel.setImageResource(hotel.imageResourceId)
        holder.textViewHotelName.text = hotel.name
        holder.textViewHotelAddress.text = hotel.address
        holder.textViewHotelPrice.text = hotel.price
        holder.ratingBarHotelRating.rating = hotel.rating.toFloat()
    }

    override fun getItemCount(): Int {
        return hotels.size
    }
    fun updateData(newHotels: List<Hotel>) {
//        hotels = newHotels
//        notifyDataSetChanged()
    }
}
