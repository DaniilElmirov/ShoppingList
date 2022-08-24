package com.example.shoppinglist.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.shoppinglist.R
import com.example.shoppinglist.domain.ShopItem
import com.google.android.material.textfield.TextInputLayout

class ShopItemFragment(
    private val screenMode: String = MODE_UNKNOWN,
    private val shopItemId: Int = ShopItem.UNDEFINED_ID, //bad solution
) : Fragment() {
    private lateinit var shopItemViewModel: ShopItemViewModel

    private lateinit var tilItemName: TextInputLayout
    private lateinit var tilItemCount: TextInputLayout
    private lateinit var etItemName: EditText
    private lateinit var etItemCount: EditText
    private lateinit var buttonSave: Button


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.fragment_shop_item, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        parseParams()

        shopItemViewModel = ViewModelProvider(this)[ShopItemViewModel::class.java]
        initViews(view)

        addTextChangeListeners()

        launchRightMode()

        observeViewModel()
    }

    private fun observeViewModel() {
        shopItemViewModel.errorInputItemCount.observe(viewLifecycleOwner) {
            val message = if (it) {
                getString(R.string.error_input_count)
            } else {
                null
            }

            tilItemCount.error = message
        }

        shopItemViewModel.errorInputItemName.observe(viewLifecycleOwner) {
            val message = if (it) {
                getString(R.string.error_input_name)
            } else {
                null
            }

            tilItemName.error = message
        }

        shopItemViewModel.shouldCloseScreen.observe(viewLifecycleOwner) {
            activity?.onBackPressed()
        }
    }

    private fun launchRightMode() {
        when (screenMode) {
            MODE_EDIT -> launchEditMode()
            MODE_ADD -> launchAddMode()
        }
    }

    private fun addTextChangeListeners() {
        etItemName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                shopItemViewModel.resetErrorInputItemName()
            }

            override fun afterTextChanged(p0: Editable?) {
            }
        })

        etItemCount.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                shopItemViewModel.resetErrorInputItemCount()
            }

            override fun afterTextChanged(p0: Editable?) {
            }
        })
    }

    private fun launchEditMode() {
        shopItemViewModel.getShopItem(shopItemId)

        shopItemViewModel.shopItem.observe(viewLifecycleOwner) {
            etItemName.setText(it.name)
            etItemCount.setText(it.count.toString())
        }

        buttonSave.setOnClickListener {
            shopItemViewModel.editShopItem(etItemName.text?.toString(),
                etItemCount.text?.toString())
        }
    }

    private fun launchAddMode() {
        buttonSave.setOnClickListener {
            shopItemViewModel.addShopItem(etItemName.text?.toString(), etItemCount.text?.toString())
        }
    }

    private fun parseParams() {
        if (screenMode != MODE_ADD && screenMode != MODE_EDIT)
            throw RuntimeException("Param screen mode is absent")

        if (screenMode == MODE_EDIT && shopItemId == ShopItem.UNDEFINED_ID)
            throw RuntimeException("Param shopItemId is absent")
    }

    private fun initViews(view: View) {
        tilItemName = view.findViewById(R.id.til_item_name)
        tilItemCount = view.findViewById(R.id.til_item_count)
        etItemName = view.findViewById(R.id.et_item_name)
        etItemCount = view.findViewById(R.id.et_item_count)
        buttonSave = view.findViewById(R.id.b_save)
    }

    companion object {
        private const val EXTRA_SCREEN_MODE = "extra_mode"
        private const val EXTRA_SHOP_ITEM_ID = "extra_shop_item_id"

        private const val MODE_EDIT = "mode_edit"
        private const val MODE_ADD = "mode_add"
        private const val MODE_UNKNOWN = ""

        fun newInstanceAddItem(): ShopItemFragment {
            return ShopItemFragment(MODE_ADD)
        }

        fun newInstanceEditItem(shopItemId: Int): ShopItemFragment {
            return ShopItemFragment(MODE_EDIT, shopItemId)
        }

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