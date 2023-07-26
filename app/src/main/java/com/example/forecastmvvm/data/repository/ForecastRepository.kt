package com.example.forecastmvvm.data.repository

import com.alialfayed.weathertask.domain.model.WeatherCityResponse
import com.example.forecastmvvm.data.ResultData
import com.example.forecastmvvm.data.db.entity.CityModel
import com.example.forecastmvvm.data.db.entity.ForecastCityModel
import kotlinx.coroutines.flow.Flow


interface ForecastRepository {
    suspend fun  getCurrentWeather(metric:Boolean): List<CityModel>
    suspend fun getFutureWeather(latitude:String, longitude:String):List<ForecastCityModel>


}