package com.example.myapp.db

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.myapp.Hotel
import com.example.myapp.R

class HotelDBHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "myapp.db"
        private const val DATABASE_VERSION = 1

        // Tên bảng và các cột trong bảng
        private const val TABLE_NAME = "hotels_room"
        private const val COLUMN_ID = "id"
        private const val COLUMN_NAME = "name"
        private const val COLUMN_ADDRESS = "address"
        private const val COLUMN_PRICE = "price"
        private const val COLUMN_IMAGE_RESOURCE_ID = "image_resource_id"
        private const val COLUMN_RATING = "rating"
    }

    override fun onCreate(db: SQLiteDatabase) {
        // Tạo bảng hotels trong cơ sở dữ liệu
        val createTableSql = """
            CREATE TABLE IF NOT EXISTS $TABLE_NAME (
                $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_NAME TEXT NOT NULL,
                $COLUMN_ADDRESS TEXT NOT NULL,
                $COLUMN_PRICE TEXT NOT NULL,
                $COLUMN_IMAGE_RESOURCE_ID INTEGER,
                $COLUMN_RATING REAL NOT NULL
            )
        """.trimIndent()
        db.execSQL(createTableSql)

        // Khởi tạo một danh sách phòng
        val hotels = mutableListOf<Hotel>()
        hotels.add(Hotel(1, "Phòng 101", "Địa chỉ 1", "100", R.drawable.ic_launcher_foreground, 4.5))
        hotels.add(Hotel(2, "Phòng 102", "Địa chỉ 2", "200", R.drawable.ic_launcher_foreground, 4.0))
        hotels.add(Hotel(3, "Phòng 103", "Địa chỉ 3", "150", R.drawable.ic_launcher_foreground, 4.2))

        // Thêm danh sách phòng vào cơ sở dữ liệu
        for (hotel in hotels) {
            addHotel(hotel)
        }

    }


    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // Xoá bảng hotels nếu nó đã tồn tại và tạo lại
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    fun addHotel(hotel: Hotel): Long {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_NAME, hotel.name)
            put(COLUMN_ADDRESS, hotel.address)
            put(COLUMN_PRICE, hotel.price)
            put(COLUMN_IMAGE_RESOURCE_ID, hotel.imageResourceId)
            put(COLUMN_RATING, hotel.rating)
        }
        return db.insert(TABLE_NAME, null, values)
    }

    fun updateHotel(hotel: Hotel): Int {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_NAME, hotel.name)
            put(COLUMN_ADDRESS, hotel.address)
            put(COLUMN_PRICE, hotel.price)
            put(COLUMN_IMAGE_RESOURCE_ID, hotel.imageResourceId)
            put(COLUMN_RATING, hotel.rating)
        }
        return db.update(TABLE_NAME, values, "$COLUMN_ID = ?", arrayOf(hotel.id.toString()))
    }

    fun deleteHotel(hotelId: Int): Int {
        val db = writableDatabase
        return db.delete(TABLE_NAME, "$COLUMN_ID = ?", arrayOf(hotelId.toString()))
    }

    fun getAllHotels(): List<Hotel>? {
        val db = readableDatabase
        val cursor = db.query(TABLE_NAME, null, null, null, null, null, null)
        val hotels = mutableListOf<Hotel>()
        while (cursor.moveToNext()) {
            val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID))
            val name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME))
            val address = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ADDRESS))
            val price = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PRICE))
            val imageResourceId = cursor.getInt(cursor.getColumnIndexOrThrow(
                COLUMN_IMAGE_RESOURCE_ID
            ))
            val rating = cursor.getFloat(cursor.getColumnIndexOrThrow(COLUMN_RATING))
            val hotel = Hotel(id, name, address, price, imageResourceId, rating.toDouble())
        }
        cursor.close()
        return null
    }

    fun searchHotels(query: String): List<Hotel> {
        val db = readableDatabase
        val cursor = db.query(
            TABLE_NAME,
            null,
            "$COLUMN_NAME LIKE ?",
            arrayOf("%$query%"),
            null,
            null,
            null
        )
        val hotels = mutableListOf<Hotel>()
        while (cursor.moveToNext()) {
            val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID))
            val name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME))
            val address = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ADDRESS))
            val price = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PRICE))
            val imageResourceId = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_IMAGE_RESOURCE_ID))
            val rating = cursor.getFloat(cursor.getColumnIndexOrThrow(COLUMN_RATING))
            val hotel = Hotel(id, name, address, price, imageResourceId, rating.toDouble())
            hotels.add(hotel)
        }
        cursor.close()
        return hotels

    }
}
