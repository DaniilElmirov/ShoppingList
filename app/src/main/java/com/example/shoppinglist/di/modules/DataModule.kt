package com.example.shoppinglist.di.modules

import android.app.Application
import com.example.shoppinglist.data.ShopListRepositoryImpl
import com.example.shoppinglist.data.database.AppDatabase
import com.example.shoppinglist.di.annotation.ApplicationScope
import com.example.shoppinglist.domain.ShopListRepository
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
interface DataModule {

    @ApplicationScope
    @Binds
    fun bindShopListRepository(impl: ShopListRepositoryImpl): ShopListRepository

    companion object {
        @ApplicationScope
        @Provides
        fun provideShopListDao(
            application: Application
        ) = AppDatabase.getInstance(application).shopListDao()
    }
}