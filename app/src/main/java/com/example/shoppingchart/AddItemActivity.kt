package com.example.shoppingchart

import android.content.Intent
import android.content.IntentFilter
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
    private lateinit var receiver: ProductReceiver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_item)

        receiver = ProductReceiver()

        productViewModel = ViewModelProvider( this, ViewModelProvider.AndroidViewModelFactory.getInstance(application))
                .get(ProductViewModel::class.java)

    }

    override fun onStart() {
        super.onStart()
        registerReceiver(receiver, IntentFilter(getString(R.string.broadcast)))
    }

    override fun onStop() {
        super.onStop()
        unregisterReceiver(receiver)
    }

    fun addItem(view: View) {
        val product = Product(pt_productName.text.toString(),et_price.text.toString().toDouble(),
                et_quantity.text.toString().toInt(), cb_isPruchesed.isChecked)
        productViewModel.insert(product)
        val broadcast = Intent(getString(R.string.broadcast))
        broadcast.putExtra("name", product.name)
        sendBroadcast(broadcast)
        Log.v("Add", "Item added")
        finish()
    }
}