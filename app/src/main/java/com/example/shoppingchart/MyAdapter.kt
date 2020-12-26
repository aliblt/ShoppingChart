package com.example.shoppingchart

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppingchart.Product
import com.example.shoppingchart.databinding.ListElementBinding
import com.google.firebase.database.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MyAdapter(val context: Context, val list: ArrayList<Product>, val ref: DatabaseReference): RecyclerView.Adapter<MyAdapter.MyViewHolder>() {


    class MyViewHolder(val binding: ListElementBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListElementBinding.inflate(inflater, parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentProduct = list[position]
        holder.binding.tvProductName.text = currentProduct.name
        holder.binding.tvPrice.text = currentProduct.price.toString()
        holder.binding.tvQuantity.text = currentProduct.quantity.toString()
        holder.binding.cbIsPurchesed.isChecked = currentProduct.isPurchased
        holder.binding.bRemove.setOnClickListener {
            ref.child(currentProduct.id).removeValue()
        }
        holder.binding.bModify.setOnClickListener{
            currentProduct.isPurchased = holder.binding.cbIsPurchesed.isChecked
            ref.child(currentProduct.id).child("purchased").setValue(currentProduct.isPurchased)
            Toast.makeText(context, "Person with id: ${currentProduct.id} is modified!", Toast.LENGTH_SHORT).show()
        }
    }

    override fun getItemCount(): Int = list.size

}