package com.example.shoppinglist.presentation

import androidx.recyclerview.widget.DiffUtil
import com.example.shoppinglist.domain.ShopItem

class ShopListDiffCallback(
    private val oldShopItemList: List<ShopItem>,
    private val newShopItemList: List<ShopItem>
): DiffUtil.Callback() {
    override fun getOldListSize() = oldShopItemList.size

    override fun getNewListSize() = newShopItemList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldShopItem = oldShopItemList[oldItemPosition]
        val newShopItem = newShopItemList[newItemPosition]

        return oldShopItem.id == newShopItem.id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldShopItem = oldShopItemList[oldItemPosition]
        val newShopItem = newShopItemList[newItemPosition]

        return oldShopItem == newShopItem
    }
}