package com.example.shoppingchart

import androidx.lifecycle.LiveData

class ProductRepo(private val productDao: ProductDao) {

    val listofProduct: LiveData<List<Product>> = productDao.getProduct()

    suspend fun insert(product: Product) {
        productDao.insert(product)
    }

    suspend fun delete(product: Product) {
        productDao.delete(product)
    }

    suspend fun update(product: Product) {
        productDao.update(product)
    }
}