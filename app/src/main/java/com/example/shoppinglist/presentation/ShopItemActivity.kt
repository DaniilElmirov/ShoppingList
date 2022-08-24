package com.example.shoppinglist.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.shoppinglist.R
import com.example.shoppinglist.domain.ShopItem
import com.google.android.material.textfield.TextInputLayout

class ShopItemActivity : AppCompatActivity() {

    //    private lateinit var shopItemViewModel: ShopItemViewModel
//
//    private lateinit var tilItemName: TextInputLayout
//    private lateinit var tilItemCount: TextInputLayout
//    private lateinit var etItemName: EditText
//    private lateinit var etItemCount: EditText
//    private lateinit var buttonSave: Button
//
    private var screenMode = MODE_UNKNOWN
    private var shopItemId = ShopItem.UNDEFINED_ID

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shop_item)

        parseIntent()
//
//        shopItemViewModel = ViewModelProvider(this)[ShopItemViewModel::class.java]
//        initViews()
//
//        addTextChangeListeners()
//
        launchRightMode()
//
//        observeViewModel()
    }

    //    private fun observeViewModel() {
//        shopItemViewModel.errorInputItemCount.observe(this) {
//            val message = if (it) {
//                getString(R.string.error_input_count)
//            } else {
//                null
//            }
//
//            tilItemCount.error = message
//        }
//
//        shopItemViewModel.errorInputItemName.observe(this) {
//            val message = if (it) {
//                getString(R.string.error_input_name)
//            } else {
//                null
//            }
//
//            tilItemName.error = message
//        }
//
//        shopItemViewModel.shouldCloseScreen.observe(this) {
//            finish()
//        }
//    }
//
    private fun launchRightMode() {
        val fragment = when (screenMode) {
            MODE_EDIT -> ShopItemFragment.newInstanceEditItem(shopItemId)
            MODE_ADD -> ShopItemFragment.newInstanceAddItem()
            else -> throw RuntimeException("Unknown screen mode $screenMode")
        }

        supportFragmentManager.beginTransaction()
            .add(R.id.container_shop_item, fragment)
            .commit()
    }

    //
//    private fun addTextChangeListeners() {
//        etItemName.addTextChangedListener(object : TextWatcher {
//            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
//            }
//
//            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
//                shopItemViewModel.resetErrorInputItemName()
//            }
//
//            override fun afterTextChanged(p0: Editable?) {
//            }
//        })
//
//        etItemCount.addTextChangedListener(object : TextWatcher {
//            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
//            }
//
//            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
//                shopItemViewModel.resetErrorInputItemCount()
//            }
//
//            override fun afterTextChanged(p0: Editable?) {
//            }
//        })
//    }
//
//    private fun launchEditMode() {
//        shopItemViewModel.getShopItem(shopItemId)
//
//        shopItemViewModel.shopItem.observe(this) {
//            etItemName.setText(it.name)
//            etItemCount.setText(it.count.toString())
//        }
//
//        buttonSave.setOnClickListener {
//            shopItemViewModel.editShopItem(etItemName.text?.toString(), etItemCount.text?.toString())
//        }
//    }
//
//    private fun launchAddMode() {
//        buttonSave.setOnClickListener {
//            shopItemViewModel.addShopItem(etItemName.text?.toString(), etItemCount.text?.toString())
//        }
//    }
//
    private fun parseIntent() {
        if (!intent.hasExtra(EXTRA_SCREEN_MODE))
            throw RuntimeException("Param screen mode is absent")

        val mode = intent.getStringExtra(EXTRA_SCREEN_MODE)

        if (mode != MODE_ADD && mode != MODE_EDIT)
            throw RuntimeException("Unknown screen mode $mode")

        screenMode = mode

        if (screenMode == MODE_EDIT) {
            if (!intent.hasExtra(EXTRA_SHOP_ITEM_ID))
                throw RuntimeException("Param shopItemId is absent")

            shopItemId = intent.getIntExtra(EXTRA_SHOP_ITEM_ID, ShopItem.UNDEFINED_ID)
        }
    }
//
//    private fun initViews() {
//        tilItemName = findViewById(R.id.til_item_name)
//        tilItemCount = findViewById(R.id.til_item_count)
//        etItemName = findViewById(R.id.et_item_name)
//        etItemCount = findViewById(R.id.et_item_count)
//        buttonSave = findViewById(R.id.b_save)
//    }

    companion object {
        private const val EXTRA_SCREEN_MODE = "extra_mode"
        private const val EXTRA_SHOP_ITEM_ID = "extra_shop_item_id"

        private const val MODE_EDIT = "mode_edit"
        private const val MODE_ADD = "mode_add"
        private const val MODE_UNKNOWN = ""

        fun newIntentAddItem(context: Context): Intent {
            val intent = Intent(context, ShopItemActivity::class.java)
            intent.putExtra(EXTRA_SCREEN_MODE, MODE_ADD)

            return intent
        }

        fun newIntentEditItem(context: Context, shopItemId: Int): Intent {
            val intent = Intent(context, ShopItemActivity::class.java)

            intent.putExtra(EXTRA_SCREEN_MODE, MODE_EDIT)
            intent.putExtra(EXTRA_SHOP_ITEM_ID, shopItemId)

            return intent
        }
    }
}