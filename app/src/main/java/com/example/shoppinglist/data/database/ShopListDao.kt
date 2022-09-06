package com.example.shoppinglist.data.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ShopListDao {
    @Query("SELECT * FROM shop_items" )
    fun getShopList(): LiveData<List<ShopItemDbModel>>

    @Query("SELECT * FROM shop_items WHERE id=:shopItemId LIMIT 1" )
    fun getShopItem(shopItemId: Int): ShopItemDbModel

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addShopItem(shopItemDbModel: ShopItemDbModel)

    @Delete
    fun deleteShopItem(shopItemId: Int)
}