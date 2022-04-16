package com.example.forecastmvvm.data.network.response.forecast


import com.google.gson.annotations.SerializedName

data class FutureWeatherResponse(
    val alerts: List<Alert>,
    val daily: List<Daily>,
    val lat: Double,
    val lon: Double,
    val timezone: String,
    @SerializedName("timezone_offset")
    val timezoneOffset: Int
)