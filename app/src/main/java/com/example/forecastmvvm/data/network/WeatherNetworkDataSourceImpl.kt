package com.example.forecastmvvm.data.network

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.forecastmvvm.data.network.response.OpenWeatherResponse
import com.example.forecastmvvm.data.network.response.forecast.FutureWeatherResponse
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

    private val _downloadedFutureWeather= MutableLiveData<FutureWeatherResponse>()
    override val downloadedFutureWeather: LiveData<FutureWeatherResponse>
    get() = _downloadedFutureWeather


    override suspend fun fetchFutureWeather(
        lon:String,
        lat:String,
        exclude:String,
        units: String
    ) {

        val lon= "92.7917"
        val lat="56.0097"
       // val exclude="current,hourly"
        val units="metric"

        try{
            val fetchedFutureWeather = openWeatherApiService
                .getForecastweather(lon,lat,exclude,units)
                .await()
            _downloadedFutureWeather.postValue(fetchedFutureWeather)


        }
        catch (e:NoConnectivityException){
            Log.e("Connectivity", "No internet connection.", e)
        }

    }
}