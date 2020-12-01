package com.example.shoppingchart

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ProductDao {

    @Query("SELECT * FROM product")
    fun getProduct(): LiveData<List<Product>>

    @Insert
    suspend fun insert(product: Product)

    @Delete
    suspend fun delete(product: Product)

    @Update
    suspend fun update(product: Product)

}
