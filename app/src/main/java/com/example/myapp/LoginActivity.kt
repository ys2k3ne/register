package com.example.myapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.myapp.db.UserDatabaseHelper

class LoginActivity : AppCompatActivity() {

    private lateinit var editTextEmail: EditText
    private lateinit var editTextPassword: EditText
    private lateinit var buttonLogin: Button
    private lateinit var textViewRegister: TextView
    private lateinit var textViewForgotPassword: TextView

    // Khai báo một đối tượng UserDatabaseHelper để thao tác với CSDL
    private lateinit var userDbHelper: UserDatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // Khởi tạo đối tượng UserDatabaseHelper
        userDbHelper = UserDatabaseHelper(this)

        // Ánh xạ các view từ layout
        editTextEmail = findViewById(R.id.editTextEmail)
        editTextPassword = findViewById(R.id.editTextPassword)
        buttonLogin = findViewById(R.id.buttonLogin)
        textViewRegister = findViewById(R.id.textViewRegister)
        textViewForgotPassword = findViewById(R.id.textViewForgotPassword)

        // Thêm sự kiện cho buttonLogin
        buttonLogin.setOnClickListener {
            // Lấy dữ liệu email và password từ EditText
            val email = editTextEmail.text.toString().trim()
            val password = editTextPassword.text.toString().trim()

            // Kiểm tra dữ liệu nhập vào
            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Vui lòng điền đầy đủ email và mật khẩu", Toast.LENGTH_SHORT)
                    .show()
                return@setOnClickListener
            }

            // Xử lý đăng nhập trên một luồng riêng biệt để không block UI
            Thread {
                val user = userDbHelper.getUserByEmail(email)
                runOnUiThread {
                    if (user != null) {
                        // Kiểm tra mật khẩu
                        if (user.password == password) {
                            // Đăng nhập thành công
                            Toast.makeText(this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show()

                            // Tiến hành chuyển đến màn hình chính
                            // Tiến hành chuyển đến màn hình chính
                            val intent = Intent(this, HomeActivity::class.java)
                            startActivity(intent)
                            finish() // Kết thúc LoginActivity để không quay lại nếu nhấn nút back
                        } else {
                            // Sai mật khẩu
                            Toast.makeText(this, "Mật khẩu không chính xác", Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        // Email không tồn tại
                        Toast.makeText(this, "Email không tồn tại", Toast.LENGTH_SHORT).show()
                    }
                }
            }.start()
        }

        // Thêm sự kiện cho textViewRegister
        textViewRegister.setOnClickListener {
            // Chuyển hướng sang trang đăng ký
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        // Thêm sự kiện cho textViewForgotPassword
        textViewForgotPassword.setOnClickListener {
            // Chuyển hướng sang trang quên mật khẩu
            val intent = Intent(this, ForgotPasswordActivity::class.java)
            startActivity(intent)
        }
    }
}
