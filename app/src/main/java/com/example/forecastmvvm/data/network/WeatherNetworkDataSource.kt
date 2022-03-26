package com.example.forecastmvvm.data.network

import androidx.lifecycle.LiveData
import com.example.forecastmvvm.data.network.response.OpenWeatherResponse


interface WeatherNetworkDataSource {
    val downloadedCurrentWeather: LiveData<OpenWeatherResponse>
   // val downloadedFutureWeather: LiveData<FutureWeatherResponse>

    suspend fun fetchCurrentWeather(
        q: String,
        units: String
    )
    suspend fun fetchFutureWeather(
        q: String,
        units: String
    )
}