package com.example.shoppingchart

import android.os.Bundle
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity

class OptionsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        var fontSize = findViewById<Spinner>(R.id.spinnerFontSize)
        var fontColor = findViewById<Spinner>(R.id.spinnerFontColor)

    }
}