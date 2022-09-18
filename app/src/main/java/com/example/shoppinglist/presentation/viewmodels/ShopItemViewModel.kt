package com.example.shoppinglist.presentation.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.shoppinglist.data.ShopListRepositoryImpl
import com.example.shoppinglist.domain.ShopItem
import com.example.shoppinglist.domain.usecases.AddShopItemUseCase
import com.example.shoppinglist.domain.usecases.EditShopItemUseCase
import com.example.shoppinglist.domain.usecases.GetShopItemUseCase
import kotlinx.coroutines.launch

class ShopItemViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = ShopListRepositoryImpl(application)

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
        viewModelScope.launch {
            val item = getShopItemUseCase.getShopItemById(shopItemId)
            _shopItem.value = item
        }
    }

    fun addShopItem(inputItemName: String?, inputItemCount: String?) {
        val itemName = parseItemName(inputItemName)
        val itemCount = parseItemCount(inputItemCount)

        val fieldsValid = validateInput(itemName, itemCount)

        if (fieldsValid) {
            viewModelScope.launch {
                val shopItem = ShopItem(itemName, itemCount, true)
                addShopItemUseCase.addShopItem(shopItem)
                closeScreen()
            }
        }
    }

    fun editShopItem(inputItemName: String?, inputItemCount: String?) {
        val itemName = parseItemName(inputItemName)
        val itemCount = parseItemCount(inputItemCount)

        val fieldsValid = validateInput(itemName, itemCount)

        if (fieldsValid) {
            _shopItem.value?.let {
                viewModelScope.launch {
                    val item = it.copy(name = itemName, count = itemCount)
                    editShopItemUseCase.editShopItem(item)
                    closeScreen()
                }
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

    fun resetErrorInputItemName() {
        _errorInputItemName.value = false
    }

    fun resetErrorInputItemCount() {
        _errorInputItemCount.value = false
    }

    private fun closeScreen() {
        _shouldCloseScreen.value = Unit
    }
}