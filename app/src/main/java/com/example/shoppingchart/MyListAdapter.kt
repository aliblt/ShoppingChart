package com.example.shoppingchart

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppingchart.databinding.ListElementBinding
import kotlin.concurrent.thread

class MyListAdapter(val context: Context, val productViewModel: ProductViewModel, private val notifiedItem: String?): RecyclerView.Adapter<MyListAdapter.ViewHolder>() {

    private var product = emptyList<Product>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListElementBinding.inflate(inflater)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentProduct = product[position]
        if ( notifiedItem != null && currentProduct.name == notifiedItem )
            setBackground(holder)
        holder.binding.tvProductName.text = currentProduct.name
        holder.binding.tvPrice.text = currentProduct.price.toString()
        holder.binding.tvQuantity.text = currentProduct.quantity.toString()
        holder.binding.cbIsPurchesed.isChecked = currentProduct.isPurchased
        holder.binding.bRemove.setOnClickListener {
            productViewModel.delete(currentProduct)
            Toast.makeText(context, "Person with id: ${currentProduct.id} is removed!", Toast.LENGTH_SHORT).show()
        }
        holder.binding.bModify.setOnClickListener{
            currentProduct.isPurchased = holder.binding.cbIsPurchesed.isChecked
            productViewModel.update(currentProduct)
            Toast.makeText(context, "Person with id: ${currentProduct.id} is modified!", Toast.LENGTH_SHORT).show()
        }
    }

    override fun getItemCount(): Int = product.size

    class ViewHolder(val binding: ListElementBinding) : RecyclerView.ViewHolder(binding.root)

    fun setProduct(product: List<Product>){
        this.product = product
        notifyDataSetChanged()
    }

    @SuppressLint("ResourceAsColor")
    fun setBackground(holder: ViewHolder){
        holder.itemView.setBackgroundColor(R.color.purple_200);
        // thread(start = true) {}
        Thread( Runnable {
            Thread.sleep(5000L)
            holder.itemView.setBackgroundColor(R.color.white)
            Log.v("Thread", "Ended")
        }).start()
    }
}