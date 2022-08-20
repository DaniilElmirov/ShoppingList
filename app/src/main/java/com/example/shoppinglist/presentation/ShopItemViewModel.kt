package com.example.shoppinglist.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
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

    private val _errorInputItemName = MutableLiveData<Boolean>()
    val errorInputItemName: LiveData<Boolean>
        get() = _errorInputItemName

    private val _errorInputItemCount = MutableLiveData<Boolean>()
    val errorInputItemCount: LiveData<Boolean>
        get() = _errorInputItemCount

    private val _shopItem = MutableLiveData<ShopItem>()
    val shopItem: LiveData<ShopItem>
        get() = _shopItem

    private val _shouldCloseScreen = MutableLiveData<Unit>()
    val shouldCloseScreen: LiveData<Unit>
        get() = _shouldCloseScreen

    fun getShopItem(shopItemId: Int) {
        val item = getShopItemUseCase.getShopItemById(shopItemId)
        _shopItem.value = item
    }

    fun addShopItem(inputItemName: String?, inputItemCount: String?) {
        val itemName = parseItemName(inputItemName)
        val itemCount = parseItemCount(inputItemCount)

        val fieldsValid = validateInput(itemName, itemCount)

        if (fieldsValid) {
            val shopItem = ShopItem(itemName, itemCount, true)
            addShopItemUseCase.addShopItem(shopItem)
            closeScreen()
        }
    }

    fun editShopItem(inputItemName: String?, inputItemCount: String?) {
        val itemName = parseItemName(inputItemName)
        val itemCount = parseItemCount(inputItemCount)

        val fieldsValid = validateInput(itemName, itemCount)

        if (fieldsValid) {
            _shopItem.value?.let {
                val item = it.copy(name = itemName, count = itemCount)
                editShopItemUseCase.editShopItem(item)
                closeScreen()
            }
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
            _errorInputItemName.value = true
            result = false
        }

        if (itemCount <= 0) {
            _errorInputItemCount.value = true
            result = false
        }

        return result
    }

    public fun resetErrorInputItemName() {
        _errorInputItemName.value = false
    }

    public fun resetErrorInputItemCount() {
        _errorInputItemCount.value = false
    }

    private fun closeScreen() {
        _shouldCloseScreen.value = Unit
    }
}