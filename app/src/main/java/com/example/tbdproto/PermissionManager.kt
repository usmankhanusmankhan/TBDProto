package com.example.tbdproto


import android.Manifest
import android.os.Build
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity

class PermissionsManager(activity: AppCompatActivity,
                         private val locationProvider: LocationProvider,
                         private val stepCounter: StepCounter) {

    // Callback. When user grants permission --> getuserlocation.
    private val locationPermissionProvider = activity.registerForActivityResult(
        ActivityResultContracts.RequestPermission()) { granted ->
        if (granted) {
            locationProvider.getUserLocation()
        }
    }
    // Callback. When user grants permission --> initialize step counter.
    private val activityRecognitionPermissionProvider = activity.registerForActivityResult(
        ActivityResultContracts.RequestPermission()) { granted ->
        if (granted) {
            stepCounter.setupStepCounter()
        }
    }

    // Function to prompt the user for Location permission
    fun requestUserLocation() {
        locationPermissionProvider.launch(Manifest.permission.ACCESS_FINE_LOCATION)
    }

    // Function to prompt the user for Fitness Activity permission
    fun requestActivityRecognition() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            activityRecognitionPermissionProvider.launch(Manifest.permission.ACTIVITY_RECOGNITION)
        } else {
            stepCounter.setupStepCounter()
        }
    }
}