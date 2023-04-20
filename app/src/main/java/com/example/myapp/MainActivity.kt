package com.example.myapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapp.adapter.HotelAdapter

class MainActivity : AppCompatActivity() {
    private lateinit var tvDangNhap : TextView
    private lateinit var tvDangKy : TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvDangKy = findViewById(R.id.tvDangKy)
        tvDangNhap = findViewById(R.id.tvDangNhap)
        tvDangKy.setOnClickListener {
            val intent= Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
        tvDangNhap.setOnClickListener {
            val intent= Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

}}
