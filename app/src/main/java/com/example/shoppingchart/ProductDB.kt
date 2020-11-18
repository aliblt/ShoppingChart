package com.example.shoppingchart

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database( entities = [Product::class], version = 1)
abstract class ProductDB : RoomDatabase() {

    abstract fun productDoa() : ProductDao

    companion object{
        private var instance: ProductDB? = null

        fun getDatabase(context: Context): ProductDB{
            var tmpInst = instance
            if( tmpInst != null )
                return tmpInst
            var inst = Room.databaseBuilder(context.applicationContext, ProductDB::class.java, "ProductDB")
                .allowMainThreadQueries().build()
            instance = inst
            return inst
        }
    }


}