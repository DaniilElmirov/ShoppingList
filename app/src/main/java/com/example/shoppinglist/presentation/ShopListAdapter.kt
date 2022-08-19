package com.example.shoppinglist.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppinglist.R
import com.example.shoppinglist.domain.ShopItem

class ShopListAdapter : RecyclerView.Adapter<ShopListAdapter.ShopItemViewHolder>() {
    var shopList = listOf<ShopItem>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShopItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.item_shop_disabled,
            parent,
            false
        )
        return ShopItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ShopItemViewHolder, position: Int) {
        val shopItem = shopList[position]

        val status = if (shopItem.enable)
            "Active"
        else
            "Not active"

        holder.view.setOnLongClickListener {
            true
        }

        if (shopItem.enable) {
            holder.tvItemName.text = "${shopItem.name} $status"
            holder.tvItemCount.text = shopItem.count.toString()

            holder.tvItemName.setTextColor(ContextCompat.getColor(
                holder.view.context,
                android.R.color.holo_red_light))
        }
    }

    override fun onViewRecycled(holder: ShopItemViewHolder) {
        super.onViewRecycled(holder)

        holder.tvItemName.text = ""
        holder.tvItemCount.text = ""

        holder.tvItemName.setTextColor(ContextCompat.getColor(
            holder.view.context,
            android.R.color.holo_red_light))
    }

    override fun getItemCount() = shopList.size

    class ShopItemViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val tvItemName = view.findViewById<TextView>(R.id.tv_item_name)
        val tvItemCount = view.findViewById<TextView>(R.id.tv_item_count)
    }
}