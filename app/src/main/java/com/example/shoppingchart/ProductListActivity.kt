package com.example.shoppingchart

import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.shoppingchart.databinding.ActivityProductListBinding
import kotlinx.android.synthetic.main.activity_product_list.*

class ProductListActivity : AppCompatActivity() {

    private lateinit var sp: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sp = getSharedPreferences("shopping_chart_pref", Context.MODE_PRIVATE )

        val binding = ActivityProductListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.rv1.layoutManager = LinearLayoutManager(this)

        Log.v("product name: ", intent.getStringExtra("product_name").toString())
        var notifiedItem: String = intent.getStringExtra("product_name").toString()

        val productViewModel = ViewModelProvider( this, ViewModelProvider.AndroidViewModelFactory.getInstance(application))
                .get(ProductViewModel::class.java)

        productViewModel.listofProduct.observe(this, Observer {product->
            product?.let { (rv1.adapter as MyListAdapter).setProduct(it) }
        })

        rv1.adapter = MyListAdapter(this, productViewModel, notifiedItem)

    }

    fun addItem(view: View) {
        val intent = Intent( baseContext, AddItemActivity::class.java)
        startActivity(intent)
    }


}