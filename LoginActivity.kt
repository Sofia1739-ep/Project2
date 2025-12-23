package com.example.kvalik

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class LoginActivity : AppCompatActivity() {

    private lateinit var prefs: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // Инициализируем SharedPreferences для сохранения имени
        prefs = getSharedPreferences("kvalik_prefs", MODE_PRIVATE)

        // Проверяем, есть ли сохраненное имя
        val savedName = prefs.getString("username", "")
        if (savedName?.isNotEmpty() == true) {
            // Если имя есть, сразу переходим на главный экран
            goToMainActivity()
        }

        // Находим кнопку
        val loginButton = findViewById<Button>(R.id.loginButton)
        val usernameEditText = findViewById<EditText>(R.id.usernameEditText)

        // Обработчик нажатия кнопки
        loginButton.setOnClickListener {
            val username = usernameEditText.text.toString().trim()

            if (username.isEmpty()) {
                Toast.makeText(this, "Введите ваше имя", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Сохраняем имя
            prefs.edit().putString("username", username).apply()

            // Переходим на главный экран
            goToMainActivity()
        }
    }

    private fun goToMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish() // Закрываем экран входа
    }
}