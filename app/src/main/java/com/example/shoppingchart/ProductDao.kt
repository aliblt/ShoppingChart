package com.example.shoppingchart

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ProductDao {

    @Query("SELECT * FROM product")
    fun getProduct(): LiveData<List<Product>>

    @Insert
    fun insert(product: Product)

    @Delete
    fun delete(product: Product)

    @Update
    fun update(product: Product)

}
