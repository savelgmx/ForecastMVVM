package com.example.forecastmvvm.internal

import com.example.forecastmvvm.data.network.response.forecast.Daily
import java.text.SimpleDateFormat
import java.util.*

class WeatherUtils {

    companion object {
        private var daily: Daily? = null
        private var latitude: Double = 0.0
        private var longitude:Double = 0.0
        private fun chooseLocalizedUnitAbbreviation(metric: String, imperial: String): String {
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
        fun updateTemperature(temperature: Int):String {
            val unitAbbreviation = chooseLocalizedUnitAbbreviation("°C", "°F")
           val temp = "$temperature$unitAbbreviation"
            return temp
        }

        fun updatePressure(pressureValue: Int):String {
            val unitAbbreviation = chooseLocalizedUnitAbbreviation("mm", "in")
            val pressure= "Pressure: $pressureValue $unitAbbreviation"
            return pressure
        }

        fun updateWind(windDirection: String, windSpeed: Int):String {
            val unitAbbreviation = chooseLocalizedUnitAbbreviation("m/sec.", "mph")
            val wind=degToCompass((windDirection).toInt())
            val windstring  = "Wind: $wind , $windSpeed $unitAbbreviation"
            return windstring
        }


        private fun degToCompass(num:Int): String {
            var winDir = Math.floor((num / 22.5) + 0.5);
            var directions = listOf<String>("North", "North North East", "North East", "East North East",
                "East", "East South East", "South East", "South South East", "South",
                "South South West", "South West", "West South West", "West", "West North West",
                "North West", "North North West")
            return directions[(winDir % 16).toInt()]

        }

        fun updateDateToToday(dt: Int?):String {
            //API returns date/time as a UnixEpoc integer timestamp
            //we must transform this with datetime format

            val simpleDateFormat = SimpleDateFormat("EEE dd MMMM yyyy", Locale.getDefault())
            var today:String="Today"
            if (dt != null) {
                today=simpleDateFormat.format(dt  * 1000L)
            }

            return today
        }

        fun updateTime(dt: Int?):String {
            //API returns date/time as a UnixEpoc integer timestamp
            //we must transform this with datetime format with time zone offset relatively to GMT into human readable date/time

            val simpleDateFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
            var currentTime="07:00"
            if (dt != null) {
                currentTime=simpleDateFormat.format(dt * 1000L)
            }
            return currentTime
        }



    }
}
