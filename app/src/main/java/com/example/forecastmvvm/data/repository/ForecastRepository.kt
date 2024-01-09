package com.example.forecastmvvm.data.repository

import com.example.forecastmvvm.data.db.entity.CurrentMainEntry
import com.example.forecastmvvm.data.db.entity.ForecastCityModel


interface ForecastRepository {
    suspend fun getCurrentWeather(metric: Boolean): CurrentMainEntry
    suspend fun getFutureWeather(latitude:String, longitude:String):List<ForecastCityModel>

    suspend fun refreshCurrentWeather()


}