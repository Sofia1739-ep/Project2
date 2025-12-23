package com.example.kvalik

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ShoppingListActivity : AppCompatActivity() {

    private lateinit var adapter: ProductAdapter
    private var listPosition: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shopping_list)

        // Получаем позицию списка из Intent
        listPosition = intent.getIntExtra("list_position", 0)

        // Берем список из MainActivity (в реальном приложении тут была бы база данных)
        val shoppingList = getShoppingList(listPosition)

        // Устанавливаем заголовок
        findViewById<TextView>(R.id.listTitleTextView).text = shoppingList.name

        // Настраиваем RecyclerView для товаров
        val recyclerView = findViewById<RecyclerView>(R.id.itemsRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = ProductAdapter(shoppingList.items.toMutableList()) { position ->
            // Обработчик удаления товара
            deleteItem(shoppingList, position)
        }
        recyclerView.adapter = adapter

        // Кнопка добавления товара
        findViewById<Button>(R.id.addItemButton).setOnClickListener {
            addNewItem(shoppingList)
        }
    }

    private fun getShoppingList(position: Int): ShoppingList {
        // В реальном приложении здесь был бы доступ к базе данных
        // Для простоты создаем тестовые данные
        return when (position) {
            0 -> ShoppingList("Покупки на неделю", mutableListOf("Молоко", "Хлеб", "Яйца"))
            1 -> ShoppingList("Для пикника", mutableListOf("Напитки", "Фрукты", "Снэки"))
            else -> ShoppingList("Список $position")
        }
    }

    private fun addNewItem(shoppingList: ShoppingList) {
        val editText = findViewById<EditText>(R.id.newItemEditText)
        val itemName = editText.text.toString().trim()

        if (itemName.isEmpty()) {
            Toast.makeText(this, "Введите название товара", Toast.LENGTH_SHORT).show()
            return
        }

        shoppingList.items.add(itemName)
        adapter.updateItems(shoppingList.items)
        editText.text.clear()
        Toast.makeText(this, "Товар добавлен", Toast.LENGTH_SHORT).show()
    }

    private fun deleteItem(shoppingList: ShoppingList, position: Int) {
        if (position >= 0 && position < shoppingList.items.size) {
            shoppingList.items.removeAt(position)
            adapter.updateItems(shoppingList.items)
            Toast.makeText(this, "Товар удален", Toast.LENGTH_SHORT).show()
        }
    }
}