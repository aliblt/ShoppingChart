package com.example.shoppingchart

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppingchart.databinding.ActivityProductListBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_product_list.*

class ProductListActivity : AppCompatActivity() {

    private lateinit var sp: SharedPreferences
    private lateinit var mAuth: FirebaseAuth
    private lateinit var userId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sp = getSharedPreferences("shopping_chart_pref", Context.MODE_PRIVATE )

        val binding = ActivityProductListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.rv1.layoutManager = LinearLayoutManager(this)
        binding.rvPrivate.layoutManager = LinearLayoutManager(this)

        mAuth = FirebaseAuth.getInstance()
        userId = mAuth.currentUser!!.uid

        val refPrivate = FirebaseDatabase.getInstance().getReference("users/$userId")
        val list2 = arrayListOf<Product>()
        getItems(list2, refPrivate, rv_private)
        rv_private.adapter = MyAdapter(this, list2, refPrivate)

        val ref = FirebaseDatabase.getInstance().getReference("shared")
        val list = arrayListOf<Product>()
        getItems(list, ref, rv1)
        rv1.adapter = MyAdapter(this, list, ref)

        Log.e("LISTSIZE123 ", list.size.toString())



/*        Log.v("product name: ", intent.getStringExtra("product_name").toString())
        var notifiedItem: String = intent.getStringExtra("product_name").toString()

        val productViewModel = ViewModelProvider( this, ViewModelProvider.AndroidViewModelFactory.getInstance(application))
                .get(ProductViewModel::class.java)

        productViewModel.listofProduct.observe(this, Observer {product->
            product?.let { (rv1.adapter as MyListAdapter).setProduct(it) }
        })

        rv1.adapter = MyListAdapter(this, productViewModel, notifiedItem)*/

    }

    fun addItem(view: View) {
        val intent = Intent( baseContext, AddItemActivity::class.java)
        intent.putExtra("uid", userId)
        startActivity(intent)
    }

    fun getItems(list: ArrayList<Product>, ref: DatabaseReference, rv: RecyclerView) {

        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                list.clear()
                for(  messageSnapshot in snapshot.children ) {
                    val name = messageSnapshot.child("name").value as String
                    val price = messageSnapshot.child("price").value as Long
                    val quantity = messageSnapshot.child("quantity").value as Long
                    val purchased = messageSnapshot.child("purchased").value as Boolean
                    val product = Product(name, price.toDouble(), quantity.toInt(), purchased)
                    product.id = messageSnapshot.child("id").value as String
                    list.add(product)
                }
                rv.adapter?.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("readDb-error", error.details)
            }
        })

        /*ref.addChildEventListener(object : ChildEventListener {
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                CoroutineScope(Dispatchers.IO).launch {
                    val name = snapshot.child("name").value as String
                    val price = snapshot.child("price").value as Long
                    val quantity = snapshot.child("quantity").value as Long
                    val purchased = snapshot.child("purchased").value as Boolean
                    val product = Product(name, price.toDouble(), quantity.toInt(), purchased)
                    list.add(product)

                    withContext(Dispatchers.Main){
                        rv.adapter?.notifyDataSetChanged()
                    }
                }
            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                val name = snapshot.child("name").value as String
                val price = snapshot.child("price").value as Long
                val quantity = snapshot.child("quantity").value as Long
                val purchased = snapshot.child("purchased").value as Boolean
                val product = Product(name,price.toDouble(),quantity.toInt(),purchased)
                CoroutineScope(Dispatchers.IO).launch {
                    list.remove(product)
                    list.add(product)
                    withContext(Dispatchers.Main){
                        rv.adapter?.notifyDataSetChanged()
                    }
                }
            }

            override fun onChildRemoved(snapshot: DataSnapshot) {
                CoroutineScope(Dispatchers.IO).launch {
                    val name = snapshot.child("name").value as String
                    val price = snapshot.child("price").value as Long
                    val quantity = snapshot.child("quantity").value as Long
                    val purchased = snapshot.child("purchased").value as Boolean
                    val product = Product(name,price.toDouble(),quantity.toInt(),purchased)
                    list.remove(product)
                    withContext(Dispatchers.Main){
                        rv.adapter?.notifyDataSetChanged()
                    }
                }
            }

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {

            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("readDb-error", error.details)
            }
        })*/
    }


}