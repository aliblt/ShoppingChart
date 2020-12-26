package com.example.shoppingchart

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var sp: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        sp = getSharedPreferences("shopping_chart_pref", Context.MODE_PRIVATE )
    }

    override fun onStart() {
        super.onStart()
        tv1.textSize = sp.getFloat("text_size", 12.0F)
        tv1.setTextColor( sp.getInt("text_color", Color.BLACK) )
    }

    override fun onResume() {
        super.onResume()
        Log.v("text_color", sp.getInt("text_color", 0).toString() )
        tv1.textSize = sp.getFloat("text_size", 12.0F)
        tv1.setTextColor( sp.getInt("text_color", Color.BLACK) )
    }

    fun mainToOption(view: View) {
        val intent = Intent( baseContext, OptionsActivity::class.java)
        startActivity(intent)
    }

    fun mainToProductList(view: View) {
        val intent = Intent( baseContext, ProductListActivity::class.java)
        startActivity(intent)
    }

    fun logOut(view: View) {
        FirebaseAuth.getInstance().signOut()
        finish()
    }

}