package com.example.forecastmvvm.data.network

import androidx.lifecycle.LiveData
import com.alialfayed.weathertask.domain.model.WeatherCityResponse
import com.example.forecastmvvm.data.ResultData
import com.example.forecastmvvm.data.network.response.CurrentWeatherResponse
import com.example.forecastmvvm.data.network.response.forecast.FutureWeatherResponse


interface WeatherNetworkDataSource {
    val downloadedCurrentWeather: LiveData<CurrentWeatherResponse>
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
    suspend fun getWeatherOfLatLon(
        latitude:String,
        longitude:String
    ): ResultData<WeatherCityResponse>

}