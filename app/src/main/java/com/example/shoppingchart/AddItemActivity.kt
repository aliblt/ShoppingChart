package com.example.shoppingchart

import android.content.ComponentName
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_add_item.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AddItemActivity : AppCompatActivity() {

    private lateinit var userID: String
    private lateinit var broadcast: Intent
    private var id = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_item)

        userID = intent.getStringExtra("uid").toString()

        /*broadcast = Intent(getString(R.string.broadcast))
        broadcast.component = ComponentName(
                "com.example.broadcast",
                "com.example.broadcast.ProductReceiver")*/
    }

    fun addItem(view: View) {

        val product = Product(pt_productName.text.toString(),et_price.text.toString().toDouble(),
                et_quantity.text.toString().toInt(), cb_isPruchesed.isChecked)

        var db = FirebaseDatabase.getInstance()
        var ref: DatabaseReference

        if( sw_private.isChecked )
            ref = db.getReference("users/$userID")
        else
            ref = db.getReference("shared")

        CoroutineScope(Dispatchers.IO).launch {
            val id = ref.push().key
            product.id = id.toString()
            ref.child(id.toString()).setValue(product)
            Log.e("KEYYY", id.toString())
        }


/*        productViewModel.insert(product)

        broadcast.putExtra("name", product.name)
        broadcast.putExtra("id", id++)
        broadcast.putExtra("channel_id", getString(R.string.channel_id))
        broadcast.putExtra("channel_name", getString(R.string.channel_name))*/

        //sendBroadcast(broadcast, "com.example.broadcast.MY_PERMISSION")


        Log.v("Add", "Item added")
        finish()
    }
}