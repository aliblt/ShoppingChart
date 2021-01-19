package com.example.shoppingchart

import android.Manifest
import android.annotation.SuppressLint
import android.app.PendingIntent
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.Geofence
import com.google.android.gms.location.GeofencingClient
import com.google.android.gms.location.GeofencingRequest
import com.google.android.gms.location.LocationServices

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_maps.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var geoClient: GeofencingClient
    private var id = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)

        geoClient = LocationServices.getGeofencingClient(this)

        val perm = arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )

        if (ActivityCompat.checkSelfPermission(
                        this,
                        Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                        this,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
        ) {
            requestPermissions( perm,0)
        }

        val perm2 = arrayOf(Manifest.permission.ACCESS_BACKGROUND_LOCATION)

        if( ActivityCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_BACKGROUND_LOCATION
        ) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions( perm2, 0)
        }


        addMarker.setOnClickListener {

            LocationServices.getFusedLocationProviderClient(this).lastLocation
                    .addOnSuccessListener {
                        if( it != null ) {
                            val place = LatLng(it.latitude, it.longitude)
                            mMap.addMarker(MarkerOptions().position(place).title(et_marker.text.toString()))
                            mMap.moveCamera(CameraUpdateFactory.newLatLng(place))
                            Toast.makeText(this, "Latitude: ${it.latitude}, Longtitude: ${it.longitude} ", Toast.LENGTH_SHORT).show()
                            addGeo(place, et_radious.text.toString().toFloat())

                            val location = SavedLocation(et_marker.text.toString(), it.latitude, it.longitude, et_radious.text.toString().toFloat(), et_description.text.toString())
                            addLocationDB(location)
                        }
                        else {
                            Toast.makeText(this, "Location is null", Toast.LENGTH_SHORT).show()
                        }
                    }

        }

        buttonPlaceList.setOnClickListener {
            val intent = Intent( baseContext, PlaceListActivity::class.java)
            startActivity(intent)
        }

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @SuppressLint("MissingPermission")
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap.isMyLocationEnabled = true

        loadMarkers(mMap)
    }

    @SuppressLint("MissingPermission")
    fun addGeo(place: LatLng, radius: Float){
        val geofence = Geofence.Builder()
            .setRequestId("Geo${++id}")
            .setCircularRegion(place.latitude, place.longitude, radius)
            .setExpirationDuration(Geofence.NEVER_EXPIRE)
            .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER)
            .build()

        val geofencingRequest = GeofencingRequest.Builder().apply {
            setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_ENTER)
            addGeofence(geofence)
        }.build()

        val pendingIntent = PendingIntent.getBroadcast(
            this,
            id,
            Intent(this, GeoReceiver::class.java),
            PendingIntent.FLAG_UPDATE_CURRENT)

        geoClient.addGeofences(geofencingRequest, pendingIntent)
                .addOnSuccessListener { Toast.makeText(this, "Geofence with id: $id added", Toast.LENGTH_SHORT).show() }
                .addOnFailureListener {  Toast.makeText(this, "Geofence Enter Failed", Toast.LENGTH_SHORT).show()
                Log.i("GEOFENCEFAIL", it.toString())}

        val geofenceExit = Geofence.Builder()
            .setRequestId("Geo${++id}")
            .setCircularRegion(place.latitude, place.longitude, radius)
            .setExpirationDuration(Geofence.NEVER_EXPIRE)
            .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_EXIT)
            .build()

        val geofencingExitRequest = GeofencingRequest.Builder()
            .setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_EXIT)
            .addGeofence(geofenceExit)
            .build()

        val pendingExitIntent = PendingIntent.getBroadcast(
            this,
            id,
            Intent(this, GeoReceiver::class.java),
            PendingIntent.FLAG_UPDATE_CURRENT)

        geoClient.addGeofences(geofencingExitRequest, pendingExitIntent)
            .addOnSuccessListener { Toast.makeText(this, "Geofence with id: $id added", Toast.LENGTH_SHORT).show() }
            .addOnFailureListener {  Toast.makeText(this, "Geofence Exit Failed", Toast.LENGTH_SHORT).show() }

    }

    fun loadMarkers(mMap: GoogleMap) {

        val ref = FirebaseDatabase.getInstance().getReference("locations")
        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (messageSnapshot in snapshot.children) {
                    val name = messageSnapshot.child("name").value as String
                    val radius = messageSnapshot.child("radius").value
                    val latitude = messageSnapshot.child("latitude").value as Double
                    val longitude = messageSnapshot.child("longitude").value as Double
                    val desc = messageSnapshot.child("description").value as String
                    val place_id = messageSnapshot.child("id").value as String
                    val place = LatLng(latitude, longitude)
                    mMap.addMarker(MarkerOptions().position(place).title(name))
                    addGeo(place, radius.toString().toFloat())
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("readDb-error", error.details)
            }
        })
    }

    fun addLocationDB(loc: SavedLocation){
        val db = FirebaseDatabase.getInstance()
        val ref: DatabaseReference

        ref = db.getReference("locations")

        CoroutineScope(Dispatchers.IO).launch {
            val id = ref.push().key
            loc.id = id.toString()
            ref.child(id.toString()).setValue(loc)
            Log.e("KEYYY", id.toString())
        }
    }

}