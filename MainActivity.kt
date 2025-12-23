package com.example.kvalik

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private lateinit var prefs: SharedPreferences
    private lateinit var shoppingLists: MutableList<ShoppingList>
    private lateinit var adapter: ShoppingListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        prefs = getSharedPreferences("kvalik_prefs", MODE_PRIVATE)

        // Получаем имя пользователя
        val username = prefs.getString("username", "Гость")
        findViewById<TextView>(R.id.welcomeTextView).text = "Привет, $username!"

        // Инициализируем списки (в реальном приложении здесь была бы база данных)
        shoppingLists = mutableListOf(
            ShoppingList("Покупки на неделю", mutableListOf("Молоко", "Хлеб", "Яйца")),
            ShoppingList("Для пикника", mutableListOf("Напитки", "Фрукты", "Снэки"))
        )

        // Настраиваем RecyclerView
        val recyclerView = findViewById<RecyclerView>(R.id.shoppingListsRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = ShoppingListAdapter(shoppingLists) { position ->
            // Обработчик клика по списку
            openShoppingList(position)
        }
        recyclerView.adapter = adapter

        // Кнопка добавления нового списка
        findViewById<Button>(R.id.addListButton).setOnClickListener {
            addNewShoppingList()
        }

        // Кнопка выхода
        findViewById<Button>(R.id.logoutButton).setOnClickListener {
            logout()
        }
    }

    private fun openShoppingList(position: Int) {
        val intent = Intent(this, ShoppingListActivity::class.java)
        intent.putExtra("list_position", position)
        startActivity(intent)
    }

    private fun addNewShoppingList() {
        // Просто добавляем тестовый список
        val newList = ShoppingList("Новый список ${shoppingLists.size + 1}")
        shoppingLists.add(newList)
        adapter.notifyItemInserted(shoppingLists.size - 1)
        Toast.makeText(this, "Список добавлен", Toast.LENGTH_SHORT).show()
    }

    private fun logout() {
        // Очищаем данные
        prefs.edit().clear().apply()

        // Возвращаемся на экран входа
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }
}

// Простой класс для списка покупок
data class ShoppingList(
    val name: String,
    val items: MutableList<String> = mutableListOf()
)