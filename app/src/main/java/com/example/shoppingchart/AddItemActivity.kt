package com.example.shoppingchart

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.activity_add_item.*
import kotlinx.android.synthetic.main.activity_product_list.*

class AddItemActivity : AppCompatActivity() {

    private lateinit var productViewModel: ProductViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_item)

    }

    fun addItem(view: View) {
        val product = Product(pt_productName.text.toString(),et_price.text.toString().toDouble(),
                et_quantity.text.toString().toInt(), cb_isPruchesed.isChecked)
        productViewModel.insert(product)
        Log.v("Add", "Item added")
        finish()
    }
}