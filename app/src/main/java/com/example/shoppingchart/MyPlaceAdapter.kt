package com.example.shoppingchart

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppingchart.databinding.ListElementBinding
import com.example.shoppingchart.databinding.PlaceListElementBinding
import com.google.firebase.database.DatabaseReference

class MyPlaceAdapter( val context: Context,   val list: ArrayList<SavedLocation>, val ref: DatabaseReference): RecyclerView.Adapter<MyPlaceAdapter.ViewHolder>(){


    class ViewHolder(val binding: PlaceListElementBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = PlaceListElementBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyPlaceAdapter.ViewHolder, position: Int) {
        val currentPlace = list[position]
        holder.binding.tvPlaceName.text = currentPlace.name
        holder.binding.tvRadius.text = currentPlace.radius.toString()
        holder.binding.tvDescription.text = currentPlace.description.toString()
        holder.binding.bRemovePlace.setOnClickListener {
            ref.child(currentPlace.id).removeValue()
        }
    }

    override fun getItemCount(): Int = list.size


}