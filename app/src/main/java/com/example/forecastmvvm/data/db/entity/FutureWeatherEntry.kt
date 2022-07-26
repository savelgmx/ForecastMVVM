package com.example.forecastmvvm.data.db.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.forecastmvvm.data.network.response.forecast.Alert
import com.example.forecastmvvm.data.network.response.forecast.Daily
import com.google.gson.annotations.SerializedName

const val FUTURE_WEATHER_ID=0

@Entity (tableName = "future_weather")
data class FutureWeatherEntry (
        @Embedded
        val alerts: List<Alert>,
        @Embedded
        val daily: List<Daily>,
        val lat: Double,
        val lon: Double,
        val timezone: String,
        @SerializedName("timezone_offset")
        val timezoneOffset: Int
)
{
    @PrimaryKey(autoGenerate = false)
    var id: Int = FUTURE_WEATHER_ID
}