package com.example.forecastmvvm.data.repository

import androidx.lifecycle.LiveData
import com.example.forecastmvvm.data.db.entity.CityModel
import com.example.forecastmvvm.data.db.entity.ForecastCityModel


interface ForecastRepository {
    suspend fun getCurrentWeather(metric:Boolean): List<CityModel>
    suspend fun getFutureWeather(latitude:String, longitude:String):LiveData<ForecastCityModel>



}