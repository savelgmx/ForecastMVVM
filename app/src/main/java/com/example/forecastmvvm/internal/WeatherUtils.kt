package com.example.forecastmvvm.internal

import com.example.forecastmvvm.data.network.response.forecast.Daily
import java.text.SimpleDateFormat
import java.util.*

class WeatherUtils {

    companion object {
        private var daily: Daily? = null
        private var latitude: Double = 0.0
        private var longitude:Double = 0.0
        fun chooseLocalizedUnitAbbreviation(metric: String, imperial: String): String {
            // return if (viewModel.isMetricUnit) metric else imperial
            return metric
        }

        fun formatDateSubtitle(dt: Int?): String {
            val simpleDateFormat = SimpleDateFormat("EEE dd MMMM yyyy", Locale.getDefault())
            return if (dt != null) {
                simpleDateFormat.format(dt * 1000L)
            } else {
                "Today" // Or another default value if dt is null
            }
        }


        //implement Daily object store/read/write
        fun getDailyObject(): Daily? {
            return daily
        }

       fun setDailyObject(daily: Daily) {
            this.daily = daily
        }

        //implement latitude and longitude store
        fun setLongitude(lon:Double){
            this.longitude=lon
        }
        fun getLongitude():Double{
            return longitude
        }
        fun setLatitude(lat:Double){
            this.latitude=lat
        }
        fun getLatitude():Double{
            return latitude
        }

        // Implement other common update functions here
        // updateTemperatures, updateCondition, updatePressure, updateWind, updateVisibility, degToCompass, etc.

    }
}
