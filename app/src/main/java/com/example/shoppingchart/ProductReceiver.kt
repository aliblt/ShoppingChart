package com.example.shoppingchart

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast

class ProductReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        Log.v("Broadcast", "received")
        Toast.makeText(context, intent.getStringExtra("name"), Toast.LENGTH_SHORT).show()
    }
}