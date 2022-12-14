package com.example.shoppinglist.presentation

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppinglist.R

class ShopItemViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
    val tvItemName = view.findViewById<TextView>(R.id.tv_item_name)
    val tvItemCount = view.findViewById<TextView>(R.id.tv_item_count)
}