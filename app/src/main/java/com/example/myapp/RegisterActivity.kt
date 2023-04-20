package com.example.myapp

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.myapp.db.UserDatabaseContract
import com.example.myapp.db.UserDatabaseHelper

class RegisterActivity : AppCompatActivity() {

    private lateinit var editTextName: EditText
    private lateinit var editTextEmail: EditText
    private lateinit var editTextPassword: EditText
    private lateinit var editTextPhone: EditText
    private lateinit var buttonRegister: Button
    private lateinit var textViewLogin: TextView
    private lateinit var userDatabaseHelper: UserDatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        // Ánh xạ các view từ layout
        editTextName = findViewById(R.id.editTextName)
        editTextEmail = findViewById(R.id.editTextEmail)
        editTextPassword = findViewById(R.id.editTextPassword)
        editTextPhone = findViewById(R.id.editTextPhone)
        buttonRegister = findViewById(R.id.buttonRegister)
        textViewLogin = findViewById(R.id.textViewLogin)

        // Khởi tạo đối tượng UserDatabaseHelper
        userDatabaseHelper = UserDatabaseHelper(this)

        // Thêm sự kiện cho buttonRegister
        buttonRegister.setOnClickListener {
            // Lấy dữ liệu từ các EditText
            val name = editTextName.text.toString()
            val email = editTextEmail.text.toString()
            val password = editTextPassword.text.toString()
            val phone = editTextPhone.text.toString()

            // Kiểm tra dữ liệu có hợp lệ hay không
            if (name.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty() && phone.isNotEmpty()) {
                // Tạo đối tượng ContentValues để chứa dữ liệu cần lưu
                val values = ContentValues()
                values.put(UserDatabaseContract.UserEntry.COLUMN_NAME, name)
                values.put(UserDatabaseContract.UserEntry.COLUMN_EMAIL, email)
                values.put(UserDatabaseContract.UserEntry.COLUMN_PASSWORD, password)
                values.put(UserDatabaseContract.UserEntry.COLUMN_PHONE, phone)

                // Lấy đối tượng SQLiteDatabase để ghi dữ liệu vào CSDL
                val db = userDatabaseHelper.writableDatabase

                // Thực hiện ghi dữ liệu vào CSDL
                val newRowId = db.insert(UserDatabaseContract.UserEntry.TABLE_NAME, null, values)

                // Khi đăng ký thành công
                if (newRowId != -1L) {
                    // Đóng kết nối đến CSDL
                    db.close()

                    val intent = Intent(this, LoginActivity::class.java)
                    intent.putExtra("email", email) // Truyền email vào Intent
                    intent.putExtra("password", password) // Truyền password vào Intent
                    startActivity(intent)

                    // Hiển thị thông báo đăng ký thành công
                    Toast.makeText(this, "Đăng ký thành công", Toast.LENGTH_SHORT).show()
                } else {
                    // Đóng kết nối đến CSDL
                    db.close()

                    // Hiển thị thông báo đăng ký thất bại
                    Toast.makeText(this, "Đăng ký thất bại", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show()
            }
        }

        // Thêm sự kiện cho textViewLogin
        textViewLogin.setOnClickListener {
            // Chuyển đến màn hình đăng nhập (LoginActivity)
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }
}
