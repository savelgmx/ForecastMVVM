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

        fun formatDateSubtitle(dt: Int?): String {
            val simpleDateFormat = SimpleDateFormat("dd MMMM yyyy", Locale.ENGLISH)
            return if (dt != null) {
                simpleDateFormat.format(dt * 1000L)
            } else {
                "Today" // Or another default value if dt is null
            }
        }
        // Implement other common update functions here
        // updateTemperatures, updateCondition, updatePressure, updateWind, updateVisibility, degToCompass, etc.

    }
}
