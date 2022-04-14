package com.example.forecastmvvm.data.network

import androidx.lifecycle.LiveData
import com.example.forecastmvvm.data.network.response.OpenWeatherResponse
import com.example.forecastmvvm.data.network.response.forecast.FutureWeatherResponse
import retrofit2.http.Query


interface WeatherNetworkDataSource {
    val downloadedCurrentWeather: LiveData<OpenWeatherResponse>
    val downloadedFutureWeather: LiveData<FutureWeatherResponse>

    suspend fun fetchCurrentWeather(
        q: String,
        units: String
    )
    suspend fun fetchFutureWeather(
        lon:String,
        lat:String,
        exclude:String="current,hourly",
        units: String
    )
}