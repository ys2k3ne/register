package com.example.myapp.db

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class UserDatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "myapp.db"
        private const val DATABASE_VERSION = 1


        // Định nghĩa thông tin cấu trúc bảng User
        private const val TABLE_NAME = "user"
        private const val COLUMN_ID = "id"
        private const val COLUMN_NAME = "name"
        private const val COLUMN_EMAIL = "email"
        private const val COLUMN_PASSWORD = "password"
        private const val COLUMN_PHONE = "phone"

        // Khai báo đối tượng UserDatabaseHelper duy nhất
        @Volatile
        private var instance: UserDatabaseHelper? = null

        // Phương thức để lấy đối tượng UserDatabaseHelper duy nhất
        fun getInstance(context: Context): UserDatabaseHelper {
            return instance ?: synchronized(this) {
                instance ?: UserDatabaseHelper(context.applicationContext).also { instance = it }
            }
        }
    }

    override fun onCreate(db: SQLiteDatabase) {
        // Tạo bảng User
        val createTable = "CREATE TABLE $TABLE_NAME " +
                "($COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "$COLUMN_NAME TEXT, " +
                "$COLUMN_EMAIL TEXT, " +
                "$COLUMN_PASSWORD TEXT, " +
                "$COLUMN_PHONE TEXT)"
        db.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // Xóa bảng User nếu đã tồn tại và tạo lại
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    // Thêm phương thức để thêm người dùng mới vào CSDL
    fun addUser(user: User) {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_NAME, user.name)
            put(COLUMN_EMAIL, user.email)
            put(COLUMN_PASSWORD, user.password)
            put(COLUMN_PHONE, user.phone)
        }
        db.insert(TABLE_NAME, null, values)
        db.close()
    }

    // Thêm phương thức để kiểm tra người dùng đã tồn tại trong CSDL
    fun checkUserExists(email: String): Boolean {
        val db = readableDatabase
        val query = "SELECT * FROM $TABLE_NAME WHERE $COLUMN_EMAIL = ?"
        val cursor = db.rawQuery(query, arrayOf(email))
        val userExists = cursor.count > 0
        cursor.close()
        db.close()
        return userExists
    }

    // Thêm phương thức để lấy thông tin người dùng từ email
    @SuppressLint("Range")
    fun getUserByEmail(email: String): User? {
        val db = readableDatabase
        val query = "SELECT * FROM $TABLE_NAME WHERE $COLUMN_EMAIL = ?"
        val cursor = db.rawQuery(query, arrayOf(email))
        var user: User? = null
        if (cursor.moveToFirst()) {
            val id = cursor.getInt(cursor.getColumnIndex(COLUMN_ID))
            val name = cursor.getString(cursor.getColumnIndex(COLUMN_NAME))
            val email = cursor.getString(cursor.getColumnIndex(COLUMN_EMAIL))
            val password = cursor.getString(cursor.getColumnIndex(COLUMN_PASSWORD))
            val phone = cursor.getString(cursor.getColumnIndex(COLUMN_PHONE))
            user = User(id, name, email, password, phone)
        }
        cursor.close()
        db.close()
        return user
    }

    // Thêm phương thức để kiểm tra thông tin đăng nhập của người dùng
    fun checkLogin(email: String, password: String): Boolean {
        val db = readableDatabase
        val query = "SELECT * FROM $TABLE_NAME WHERE $COLUMN_EMAIL = ? AND $COLUMN_PASSWORD = ?"
        val cursor = db.rawQuery(query, arrayOf(email, password))
        val loginSuccess = cursor.count > 0
        cursor.close()
        db.close()
        return loginSuccess
    }
}
