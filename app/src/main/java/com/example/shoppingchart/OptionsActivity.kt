package com.example.shoppingchart

import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_options.*

class OptionsActivity : AppCompatActivity() {

    private lateinit var sp: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_options)
        sp = getPreferences(Context.MODE_PRIVATE)
        editor = sp.edit()

        val colors = resources.getStringArray(R.array.Colors)

        val colorSpinner = findViewById<Spinner>(R.id.spinnerFontColor)
        if( colorSpinner != null )
            colorSpinner.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, colors)

    }

    override fun onStart() {
        super.onStart()
        textFontSize.textSize = sp.getFloat( "text_size", 12.0F)
        textFontSize.setTextColor( sp.getInt("text_color", Color.RED ) )

        textFontColor.textSize = sp.getFloat( "text_size", 12.0F)
        textFontColor.setTextColor( sp.getInt("text_color", Color.RED ) )
    }

    fun saveState(view: View) {
        editor.putFloat("text_size", 12.0F) // TODO change constant
        editor.putInt("text_color", Color.RED)
        editor.apply()
        Toast.makeText(baseContext, "Saved", Toast.LENGTH_SHORT).show()
    }

    fun optionToMain(view: View) {

    }
}