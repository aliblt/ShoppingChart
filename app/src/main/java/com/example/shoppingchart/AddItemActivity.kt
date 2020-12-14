package com.example.shoppingchart

import android.Manifest
import android.content.ComponentName
import android.content.Intent
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Gravity.apply
import android.view.View
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.activity_add_item.*

class AddItemActivity : AppCompatActivity() {

    private lateinit var productViewModel: ProductViewModel
    private lateinit var broadcast: Intent
    private var id = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_item)

        broadcast = Intent(getString(R.string.broadcast))
        broadcast.component = ComponentName(
                "com.example.broadcast",
                "com.example.broadcast.ProductReceiver")

        productViewModel = ViewModelProvider( this, ViewModelProvider.AndroidViewModelFactory.getInstance(application))
                .get(ProductViewModel::class.java)

    }

    override fun onStart() {
        super.onStart()
        //registerReceiver(receiver2, IntentFilter(getString(R.string.broadcast)))
    }

    override fun onStop() {
        super.onStop()
        //unregisterReceiver(receiver2)
    }

    fun addItem(view: View) {

        val product = Product(pt_productName.text.toString(),et_price.text.toString().toDouble(),
                et_quantity.text.toString().toInt(), cb_isPruchesed.isChecked)
        productViewModel.insert(product)

/*        broadcast.putExtra("name", product.name)
        broadcast.putExtra("id", id++)
        broadcast.putExtra("channel_id", getString(R.string.channel_id))
        broadcast.putExtra("channel_name", getString(R.string.channel_name))*/

        //sendBroadcast(broadcast, "com.example.broadcast.MY_PERMISSION")


        Log.v("Add", "Item added")
        finish()
    }
}