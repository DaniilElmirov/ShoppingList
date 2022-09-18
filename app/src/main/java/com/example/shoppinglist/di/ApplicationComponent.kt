package com.example.shoppinglist.di

import android.app.Application
import com.example.shoppinglist.di.annotation.ApplicationScope
import com.example.shoppinglist.di.modules.DataModule
import com.example.shoppinglist.di.modules.ViewModelModule
import com.example.shoppinglist.presentation.ShopItemFragment
import com.example.shoppinglist.presentation.activities.MainActivity
import dagger.BindsInstance
import dagger.Component

@ApplicationScope
@Component(modules = [DataModule::class, ViewModelModule::class])
interface ApplicationComponent {

    fun inject(activity: MainActivity)

    fun inject(fragment: ShopItemFragment)

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance application: Application): ApplicationComponent
    }
}