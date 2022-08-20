package com.example.shoppinglist.presentation

import androidx.lifecycle.ViewModel
import com.example.shoppinglist.data.ShopListRepositoryImpl
import com.example.shoppinglist.domain.AddShopItemUseCase
import com.example.shoppinglist.domain.EditShopItemUseCase
import com.example.shoppinglist.domain.GetShopItemUseCase
import com.example.shoppinglist.domain.ShopItem

class ShopItemViewModel : ViewModel() {
    private val repository = ShopListRepositoryImpl

    private val getShopItemUseCase = GetShopItemUseCase(repository)
    private val addShopItemUseCase = AddShopItemUseCase(repository)
    private val editShopItemUseCase = EditShopItemUseCase(repository)

    fun getShopItem(shopItemId: Int) {
        val item = getShopItemUseCase.getShopItemById(shopItemId)
    }

    fun addShopItem(inputItemName: String?, inputItemCount: String?) {
        val itemName = parseItemName(inputItemName)
        val itemCount = parseItemCount(inputItemCount)

        val fieldsValid = validateInput(itemName, itemCount)

        if (fieldsValid) {
            val shopItem = ShopItem(itemName, itemCount, true)
            addShopItemUseCase.addShopItem(shopItem)
        }
    }

    fun editShopItem(inputItemName: String?, inputItemCount: String?) {
        val itemName = parseItemName(inputItemName)
        val itemCount = parseItemCount(inputItemCount)

        val fieldsValid = validateInput(itemName, itemCount)

        if (fieldsValid) {
            val shopItem = ShopItem(itemName, itemCount, true)
            editShopItemUseCase.editShopItem(shopItem)
        }
    }

    private fun parseItemName(inputItemName: String?) = inputItemName?.trim() ?: ""

    private fun parseItemCount(inputItemCount: String?) = try {
        inputItemCount?.trim()?.toInt() ?: 0
    } catch (e: Exception) {
        0
    }

    private fun validateInput(itemName: String, itemCount: Int): Boolean {
        var result = true

        if (itemName.isBlank()) {
            //TODO: show error input name
            result = false
        }

        if (itemCount <= 0) {
            //TODO: show error input count
            result = false
        }

        return result
    }
}