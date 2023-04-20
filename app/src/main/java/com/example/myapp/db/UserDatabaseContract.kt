package com.example.myapp.db

import android.provider.BaseColumns

object UserDatabaseContract {
    // Định nghĩa tên bảng và các tên cột trong bảng User
    object UserEntry : BaseColumns {
        const val TABLE_NAME = "user"
        const val COLUMN_NAME = "name"
        const val COLUMN_EMAIL = "email"
        const val COLUMN_PASSWORD = "password"
        const val COLUMN_PHONE = "phone"
    }
}
