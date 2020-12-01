package com.example.shoppingchart

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

class ProductViewModel(app: Application) : AndroidViewModel(app) {

    private val repo: ProductRepo
    val listofProduct: LiveData<List<Product>>

    init {
        val productDao: ProductDao = ProductDB.getDatabase(app).productDoa()
        repo = ProductRepo(productDao)
        listofProduct = repo.listofProduct
    }

    fun insert(product: Product) {
        CoroutineScope(IO).launch {
            repo.insert(product)
        }
    }

    fun delete(product: Product) {
        CoroutineScope(IO).launch {
            repo.delete(product)
        }
    }

    fun update(product: Product) {
        CoroutineScope(IO).launch {
            repo.update(product)
        }
    }
}