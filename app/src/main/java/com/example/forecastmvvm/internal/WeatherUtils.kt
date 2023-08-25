package com.example.forecastmvvm.internal

import androidx.appcompat.app.AppCompatActivity
import java.text.SimpleDateFormat
import java.util.*

class WeatherUtils {
    companion object {
        fun chooseLocalizedUnitAbbreviation(metric: String, imperial: String): String {
            // return if (viewModel.isMetricUnit) metric else imperial
            return metric
        }

        fun updateLocationTitle(activity: AppCompatActivity, location: String) {
            activity.supportActionBar?.title = location
        }

        fun updateDateSubtitle(activity: AppCompatActivity, dt: Int?) {
            val simpleDateFormat = SimpleDateFormat("dd MMMM yyyy, HH:mm:ss", Locale.ENGLISH)
            val today = dt?.let { simpleDateFormat.format(it * 1000L) } ?: "Today"
            activity.supportActionBar?.subtitle = today
        }

        // Implement other common update functions here
        // updateTemperatures, updateCondition, updatePressure, updateWind, updateVisibility, degToCompass, etc.

    }
}
