package com.example.shoppinglist.domain.usecases

import com.example.shoppinglist.domain.ShopItem
import com.example.shoppinglist.domain.ShopListRepository
import javax.inject.Inject

class GetShopItemUseCase @Inject constructor(private val shopListRepository: ShopListRepository) {
    suspend fun getShopItemById(shopItemId: Int): ShopItem {
        return shopListRepository.getShopItemById(shopItemId)
    }
}