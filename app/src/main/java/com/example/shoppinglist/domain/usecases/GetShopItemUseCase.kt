package com.example.shoppinglist.domain.usecases

import com.example.shoppinglist.domain.ShopItem
import com.example.shoppinglist.domain.ShopListRepository

class GetShopItemUseCase(private val shopListRepository: ShopListRepository) {
    suspend fun getShopItemById(shopItemId: Int): ShopItem {
        return shopListRepository.getShopItemById(shopItemId)
    }
}