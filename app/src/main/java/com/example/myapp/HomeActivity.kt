package com.example.myapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapp.Hotel
import com.example.myapp.R
import com.example.myapp.adapter.HotelAdapter
import com.example.myapp.db.HotelDBHelper

class HomeActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var hotelAdapter: HotelAdapter
    private var hotels: List<Hotel> = listOf() // Dữ liệu danh sách khách sạn
    private lateinit var dbHelper: HotelDBHelper // Đối tượng DBHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        dbHelper = HotelDBHelper(this)

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)


        // Khởi tạo adapter và gán cho RecyclerView
        hotelAdapter = HotelAdapter(hotels)
        recyclerView.adapter = hotelAdapter

        // Lấy dữ liệu khách sạn từ nguồn dữ liệu (ví dụ: database, API)
        getHotelData()

        // Cập nhật dữ liệu cho adapter
        updateHotelData(hotels)
    }

    private fun getHotelData() {
        // Lấy dữ liệu khách sạn từ cơ sở dữ liệu SQLite
    }

    // Hàm cập nhật dữ liệu mới cho adapter và thông báo cập nhật
    private fun updateHotelData(hotels: List<Hotel>) {
        hotelAdapter.updateData(hotels)
//        hotelAdapter.notifyDataSetChanged()
    }
}
