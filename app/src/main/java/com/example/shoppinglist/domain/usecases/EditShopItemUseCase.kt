package com.example.shoppinglist.domain.usecases

import com.example.shoppinglist.domain.ShopItem
import com.example.shoppinglist.domain.ShopListRepository

class EditShopItemUseCase(private val shopListRepository: ShopListRepository) {
    fun editShopItem(shopItem: ShopItem){
        shopListRepository.editShopItem(shopItem)
    }
}