package com.example.tbdproto

import android.content.Context.SENSOR_SERVICE
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.hardware.SensorManager.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData

class StepCounter(private val activity: AppCompatActivity) : SensorEventListener {

    interface StepUpdater {

        fun onStepUpdated(steps: Int)
    }
    // List of live data. Stream*** of ints to store the live count of steps.
    val liveSteps = MutableLiveData<Int>()

    // Lazy singleton instantiaton of SensorService.
    private val sensorManager by lazy {
        activity.getSystemService(SENSOR_SERVICE) as SensorManager
    }
    // Lazy singleton instantiaton of StepCounterSensor.
    private val stepCounterSensor: Sensor? by lazy {
        sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)
    }

    // Variable to store steps. Initialized to -1
    private var initialSteps = -1

    // Init function for the stepCounter service
    fun setupStepCounter() {
        if (stepCounterSensor != null) {
            sensorManager.registerListener(this, stepCounterSensor, SENSOR_DELAY_FASTEST)
        }
    }
    // Listener of the step count sensor
    override fun onSensorChanged(event: SensorEvent) {
        event.values.firstOrNull()?.toInt()?.let { newSteps ->
            if (initialSteps == -1) {
                initialSteps = newSteps
            }

            val currentSteps = newSteps - initialSteps

            liveSteps.value = currentSteps
        }
    }
    // Stop Listening to the step counter sensor
    fun unloadStepCounter() {
        if (stepCounterSensor != null) {
            sensorManager.unregisterListener(this)
        }
    }

    override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) = Unit
}