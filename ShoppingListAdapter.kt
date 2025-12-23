package com.example.kvalik

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ShoppingListAdapter(
    private val shoppingLists: List<ShoppingList>,
    private val onItemClick: (Int) -> Unit
) : RecyclerView.Adapter<ShoppingListAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val listNameTextView: TextView = view.findViewById(R.id.listNameTextView)
        val itemCountTextView: TextView = view.findViewById(R.id.itemCountTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_shopping_list, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val list = shoppingLists[position]

        holder.listNameTextView.text = list.name
        holder.itemCountTextView.text = list.items.size.toString()

        // Клик по всему элементу
        holder.itemView.setOnClickListener {
            onItemClick(position)
        }
    }

    override fun getItemCount() = shoppingLists.size
}