package com.example.shoppinglist.presentation

import androidx.lifecycle.ViewModel
import com.example.shoppinglist.data.ShopListRepositoryImpl
import com.example.shoppinglist.domain.usecases.DeleteShopItemUseCase
import com.example.shoppinglist.domain.usecases.EditShopItemUseCase
import com.example.shoppinglist.domain.usecases.GetShopListUseCase
import com.example.shoppinglist.domain.ShopItem

class MainViewModel : ViewModel() {
    private val repository = ShopListRepositoryImpl

    private val getShopListUseCase = GetShopListUseCase(repository)
    private val deleteShopItemUseCase = DeleteShopItemUseCase(repository)
    private val editShopListUseCase = EditShopItemUseCase(repository)

    val shopList = getShopListUseCase.getShopList()

    fun deleteShopItem(shopItem: ShopItem) {
        deleteShopItemUseCase.deleteShopItem(shopItem)
    }

    fun changeEnableState(shopItem: ShopItem){
        val newItem = shopItem.copy(enabled = !shopItem.enabled)
        editShopListUseCase.editShopItem(newItem)
    }
}