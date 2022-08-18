package com.example.shoppinglist.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.shoppinglist.R
import com.example.shoppinglist.domain.ShopItem

class MainActivity : AppCompatActivity() {
    private lateinit var mainViewModel: MainViewModel
    private lateinit var llShopList: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        llShopList = findViewById(R.id.ll_shop_list)

        mainViewModel = ViewModelProvider(this)[MainViewModel::class.java]
        mainViewModel.shopList.observe(this) {
            showShopList(it)
        }
    }

    private fun showShopList(list: List<ShopItem>) {
        llShopList.removeAllViews()

        for (shopItem in list) {
            val layoutId = if (shopItem.enable)
                R.layout.item_shop_enabled
            else
                R.layout.item_shop_disabled

            val view = LayoutInflater.from(this).inflate(layoutId, llShopList, false)

            val tvItemName = view.findViewById<TextView>(R.id.tv_item_name)
            tvItemName.text = shopItem.name

            val tvItemCount = view.findViewById<TextView>(R.id.tv_item_count)
            tvItemCount.text = shopItem.count.toString()

            view.setOnLongClickListener {
                mainViewModel.changeEnableState(shopItem)
                true
            }

            llShopList.addView(view)

        }
    }
}