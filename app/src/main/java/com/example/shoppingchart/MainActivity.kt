package com.example.shoppingchart

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var sp: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        sp = getPreferences(Context.MODE_PRIVATE)
    }

    override fun onStart() {
        super.onStart()
        tv1.textSize = sp.getFloat("text_size", 12.0F)
        tv1.setTextColor( sp.getInt("text_color", Color.BLACK) )
    }


    fun mainToOption(view: View) {
        val intent = Intent( baseContext  , OptionsActivity::class.java)
        startActivity(intent)
    }

}