package com.example.forecastmvvm.data.network

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.forecastmvvm.data.network.response.OpenWeatherResponse
import com.example.forecastmvvm.internal.NoConnectivityException

class WeatherNetworkDataSourceImpl(
    private val openWeatherApiService:OpenWeatherApiService
) : WeatherNetworkDataSource {
    private val _downloadedCurrentWeather = MutableLiveData<OpenWeatherResponse>()
    override val downloadedCurrentWeather: LiveData<OpenWeatherResponse>
        get() = _downloadedCurrentWeather

    override suspend fun fetchCurrentWeather(q: String, units: String) {
        try {
            val fetchedCurrentWeather = openWeatherApiService
                .getCurrentWeather(q,units)
                .await()
            _downloadedCurrentWeather.postValue(fetchedCurrentWeather)
        }
        catch (e: NoConnectivityException) {
            Log.e("Connectivity", "No internet connection.", e)
        }
    }

    override suspend fun fetchFutureWeather(q: String, units: String) {
        TODO("Not yet implemented")

    }
}