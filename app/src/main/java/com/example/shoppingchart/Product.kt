package com.example.shoppingchart

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Product( var name:String, var price: Double, var quantity: Int, var isPurchased: Boolean ) {

    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
}