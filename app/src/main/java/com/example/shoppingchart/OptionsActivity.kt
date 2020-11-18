package com.example.shoppingchart

import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.EditText
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
        sp = getSharedPreferences("shopping_chart_pref", Context.MODE_PRIVATE )
        editor = sp.edit()

        val colors = resources.getStringArray(R.array.Colors)

        var editTextSize = findViewById<EditText>(R.id.editTextNumber)
        editTextSize.setText( sp.getFloat("text_size", 12.0F).toString() )

        val colorSpinner = findViewById<Spinner>(R.id.spinnerFontColor)
        if( colorSpinner != null )
            colorSpinner.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, colors)

        colorSpinner.setSelection(0)

    }

    fun setFont() {
        textFontSize.textSize = sp.getFloat( "text_size", 12.0F)
        textFontSize.setTextColor( sp.getInt("text_color", Color.RED ) )

        textFontColor.textSize = sp.getFloat( "text_size", 12.0F)
        textFontColor.setTextColor( sp.getInt("text_color", Color.RED ) )
    }

    override fun onStart() {
        super.onStart()
        setFont()
    }

    fun saveState(view: View) {
        val savedFontSize = editTextNumber.text.toString().toFloat()
        var selectedColor = Color.BLACK

        when( spinnerFontColor.selectedItem.toString() ) {
            "Red" -> selectedColor = Color.RED
            "Blue" -> selectedColor = Color.BLUE
            "Black" -> selectedColor = Color.BLACK
            "Purple"  -> selectedColor = 0xFF6200EE.toInt()
            "Green" -> selectedColor = Color.GREEN
        }

        editor.putFloat("text_size", savedFontSize )
        editor.putInt("text_color", selectedColor)
        editor.apply()
        setFont()
        Toast.makeText(baseContext, "Saved", Toast.LENGTH_SHORT).show()
    }

    fun optionToMain(view: View) {
        finish()
    }
}