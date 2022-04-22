package com.example.tbdproto
import android.annotation.SuppressLint
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.SphericalUtil
import kotlin.math.roundToInt

@SuppressLint("MissingPermission")
class LocationProvider(private val activity: AppCompatActivity) {

    // Lazy singleton instantiaton of LocationServices.
    private val client by lazy { LocationServices.getFusedLocationProviderClient(activity) }

    // Mutable list of LatLng objects to store locations
    private val locations = mutableListOf<LatLng>()
    // Distance variable initialized at 0.
    private var distance = 0

    //Streams of live location/s and distance
    val liveLocations = MutableLiveData<List<LatLng>>()
    val liveDistance = MutableLiveData<Int>()
    val liveLocation = MutableLiveData<LatLng>()

    // Override LocationCallback onLocationResult to update our variables and compute distance.
    private val locationCallback = object : LocationCallback() {
        override fun onLocationResult(result: LocationResult) {
            val currentLocation = result.lastLocation
            val latLng = LatLng(currentLocation.latitude, currentLocation.longitude)

            val lastLocation = locations.lastOrNull()

            if (lastLocation != null) {
                distance += SphericalUtil.computeDistanceBetween(lastLocation, latLng).roundToInt()
                liveDistance.value = distance
            }

            locations.add(latLng)
            liveLocations.value = locations
        }
    }
    // Fun to add location to locations and forward the value over liveLocations
    fun getUserLocation() {
        client.lastLocation.addOnSuccessListener { location ->
            val latLng = LatLng(location.latitude, location.longitude)
            locations.add(latLng)
            liveLocation.value = latLng
        }
    }
    // Fun to start tracking and request fitness activity permisson
    fun trackUser() {
        val locationRequest = LocationRequest.create()
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        locationRequest.interval = 5000
        client.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper())
    }
    // Fun to stop tracking
    fun stopTracking() {
        client.removeLocationUpdates(locationCallback)
        locations.clear()
        distance = 0
    }
}