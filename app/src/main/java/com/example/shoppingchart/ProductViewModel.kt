package com.example.shoppingchart

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData

class ProductViewModel(app: Application) : AndroidViewModel(app) {

    private val repo: ProductRepo
    val listofProduct: LiveData<List<Product>>

    init {
        val productDao: ProductDao = ProductDB.getDatabase(app).productDoa()
        repo = ProductRepo(productDao)
        listofProduct = repo.listofProduct
    }

    fun insert(product: Product) = repo.insert(product)

    fun delete(product: Product) = repo.delete(product)

    fun update(product: Product) = repo.update(product)
}