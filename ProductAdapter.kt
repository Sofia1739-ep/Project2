package com.example.kvalik

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView

class ProductAdapter(
    private var products: List<String>,
    private val onDeleteClick: (Int) -> Unit
) : RecyclerView.Adapter<ProductAdapter.ViewHolder>() {

    private val checkedItems = mutableSetOf<Int>()

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val checkBox: CheckBox = view.findViewById(R.id.itemCheckBox)
        val nameTextView: TextView = view.findViewById(R.id.itemNameTextView)
        val deleteButton: Button = view.findViewById(R.id.deleteButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_product, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val productName = products[position]

        holder.nameTextView.text = productName
        holder.checkBox.isChecked = checkedItems.contains(position)

        // Отмечаем товар как купленный/некупленный
        holder.checkBox.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                checkedItems.add(position)
                // Зачеркиваем текст для купленных товаров
                holder.nameTextView.apply {
                    paintFlags = paintFlags or android.graphics.Paint.STRIKE_THRU_TEXT_FLAG
                    alpha = 0.6f
                }
            } else {
                checkedItems.remove(position)
                // Убираем зачеркивание
                holder.nameTextView.apply {
                    paintFlags = paintFlags and android.graphics.Paint.STRIKE_THRU_TEXT_FLAG.inv()
                    alpha = 1.0f
                }
            }
        }

        // Кнопка удаления товара
        holder.deleteButton.setOnClickListener {
            onDeleteClick(position)
        }
    }

    override fun getItemCount() = products.size

    fun updateItems(newProducts: List<String>) {
        products = newProducts
        notifyDataSetChanged()
    }

    fun getCheckedItems(): Set<Int> = checkedItems
}