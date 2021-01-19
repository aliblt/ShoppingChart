package com.example.shoppingchart

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppingchart.databinding.ActivityPlaceListBinding
import com.example.shoppingchart.databinding.ActivityProductListBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_place_list.*
import kotlinx.android.synthetic.main.activity_product_list.*

class PlaceListActivity : AppCompatActivity() {

    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_place_list)

        val binding = ActivityPlaceListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.rvPlace.layoutManager = LinearLayoutManager(this)

        mAuth = FirebaseAuth.getInstance()

        val ref = FirebaseDatabase.getInstance().getReference("locations")
        val list = arrayListOf<SavedLocation>()
        getItems(list, ref, rv_place)
        rv_place.adapter = MyPlaceAdapter(this, list, ref)

    }

    fun getItems(list: ArrayList<SavedLocation>, ref: DatabaseReference, rv: RecyclerView) {

        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                list.clear()
                for (messageSnapshot in snapshot.children) {
                    val name = messageSnapshot.child("name").value as String
                    val radius = messageSnapshot.child("radius").value
                    val latitude = messageSnapshot.child("latitude").value as Double
                    val longitude = messageSnapshot.child("longitude").value as Double
                    val desc = messageSnapshot.child("description").value as String
                    val place = SavedLocation(name, latitude, longitude, radius.toString().toFloat(), desc)
                    place.id = messageSnapshot.child("id").value as String
                    list.add(place)
                }
                rv.adapter?.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("readDb-error", error.details)
            }
        })
    }
}