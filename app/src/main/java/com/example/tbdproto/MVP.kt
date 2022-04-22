package com.example.tbdproto

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.maps.model.LatLng

// Data class to store the data of the view. ViewModel
data class Ui(
    val formattedPace: String,
    val formattedDistance: String,
    val currentLocation: LatLng?,
    val userPath: List<LatLng>
) {

    companion object {

        val EMPTY = Ui(
            formattedPace = "",
            formattedDistance = "",
            currentLocation = null,
            userPath = emptyList()
        )
    }
}


class MapPresenter(private val activity: AppCompatActivity) {
    val ui = MutableLiveData(Ui.EMPTY)
    private val locationProvider = LocationProvider(activity)
    private val stepCounter = StepCounter(activity)
    private val permissionsManager = PermissionsManager(activity, locationProvider, stepCounter)

    // Set listeners for the live data parameters steps, location and distance.
    fun onViewCreated() {
        stepCounter.liveSteps.observe(activity) { steps ->
            val current = ui.value
            ui.value = current?.copy(formattedPace = "$steps")
        }

        locationProvider.liveLocations.observe(activity) { locations ->
            val current = ui.value
            ui.value = current?.copy(userPath = locations)
        }

        locationProvider.liveLocation.observe(activity) { currentLocation ->
            val current = ui.value
            ui.value = current?.copy(currentLocation = currentLocation)
        }

        locationProvider.liveDistance.observe(activity) { distance ->
            val current = ui.value
            val formattedDistance = activity.getString(R.string.distance_value, distance)
            ui.value = current?.copy(formattedDistance = formattedDistance)
        }
    }
    //When the map is loaded, we request for user location
    fun onMapLoaded() {
        permissionsManager.requestUserLocation()
    }
    // Fun to start tracking and request fitness activity permisson
    fun startTracking() {
        locationProvider.trackUser()
        permissionsManager.requestActivityRecognition()
    }
    // Fun to stop tracking
    fun stopTracking() {
        locationProvider.stopTracking()
        stepCounter.unloadStepCounter()
    }
}