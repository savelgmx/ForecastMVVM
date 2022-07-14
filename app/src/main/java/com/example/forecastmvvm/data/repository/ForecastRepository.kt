package com.example.forecastmvvm.data.repository

import androidx.lifecycle.LiveData
import com.example.forecastmvvm.data.ResultData
import com.example.forecastmvvm.data.db.entity.CityModel
import com.example.forecastmvvm.data.db.entity.ForecastCityModel
import com.example.forecastmvvm.data.network.response.forecast.FutureWeatherResponse
import kotlinx.coroutines.flow.Flow


interface ForecastRepository {
    suspend fun getCurrentWeather(metric:Boolean): List<CityModel>
    suspend fun getFutureWeather(latitude:String, longitude:String):LiveData<ForecastCityModel>
    fun getWeatherOfLatLon(): Flow<ResultData<FutureWeatherResponse>>


}