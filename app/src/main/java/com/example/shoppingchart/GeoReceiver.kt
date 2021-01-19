package com.example.shoppingchart

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import com.google.android.gms.location.Geofence
import com.google.android.gms.location.GeofencingEvent

class GeoReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {

        val geoEvent = GeofencingEvent.fromIntent(intent)
        val triggering = geoEvent.triggeringGeofences

        for( geo in triggering) {
            //Toast.makeText(context, "Geofence with id:${geo.requestId} is triggered", Toast.LENGTH_SHORT).show()

            if(geoEvent.geofenceTransition == Geofence.GEOFENCE_TRANSITION_ENTER){
                Toast.makeText(context, "Entered ${geo.requestId}", Toast.LENGTH_SHORT).show()
            }
            else if(geoEvent.geofenceTransition == Geofence.GEOFENCE_TRANSITION_EXIT){
                Toast.makeText(context, "Exited ${geo.requestId}", Toast.LENGTH_SHORT).show()
            }
            else{
                Log.e("geofences", "Error")
            }
        }


    }
}