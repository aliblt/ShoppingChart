package com.example.shoppingchart

import androidx.lifecycle.LiveData

class ProductRepo(private val productDao: ProductDao) {

    val listofProduct: LiveData<List<Product>> = productDao.getProduct()

    fun insert(product: Product) {
        productDao.insert(product)
    }

    fun delete(product: Product) {
        productDao.delete(product)
    }
}