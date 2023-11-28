package com.example.forecastmvvm.data.repository

import androidx.lifecycle.LiveData
import com.example.forecastmvvm.data.db.entity.CurrentWeatherEntry
import com.example.forecastmvvm.data.db.entity.ForecastCityModel


interface ForecastRepository {
    suspend fun getCurrentWeather(metric: Boolean): CurrentWeatherEntry
    suspend fun getFutureWeather(latitude:String, longitude:String):List<ForecastCityModel>

    suspend fun refreshCurrentWeather()


}