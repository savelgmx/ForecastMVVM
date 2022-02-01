package com.example.forecastmvvm.data.network

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.forecastmvvm.data.network.response.CurrentWeatherResponse
import com.example.forecastmvvm.internal.NoConnectivityException

class WeatherNetworkDataSourceImpl(
    private val weatherstackApiService:WeatherstackApiService
) : WeatherNetworkDataSource {
    private val _downloadedCurrentWeather = MutableLiveData<CurrentWeatherResponse>()
    override val downloadedCurrentWeather: LiveData<CurrentWeatherResponse>
        get() = _downloadedCurrentWeather

    override suspend fun fetchCurrentWeather(location: String, languageCode: String) {
        try {
            val fetchedCurrentWeather = weatherstackApiService
                .getCurrentWeather(location, languageCode)
                .await()
            _downloadedCurrentWeather.postValue(fetchedCurrentWeather)
        }
        catch (e: NoConnectivityException) {
            Log.e("Connectivity", "No internet connection.", e)
        }
    }

    override suspend fun fetchFutureWeather(location: String, languageCode: String) {
        TODO("Not yet implemented")
        //        val apiService = WeatherstackApiService()
        //        val apiService = WeatherstackApiService(ConnectivityInterceptorImpl(this.context!!))
        //        val weatherNetworkDataSource = WeatherNetworkDataSourceImpl(apiService)
        //        weatherNetworkDataSource.downloadedCurrentWeather.observe(this,
        //            Observer {
        //                textView.text= it.toString()
        //        })
        //
        //        GlobalScope.launch(Dispatchers.Main) {
        //            val currentWeatherResponse = apiService.getCurrentWeather("Krasnoyarsk").await()
        //            textView.text= currentWeatherResponse.toString()
        //            weatherNetworkDataSource.fetchCurrentWeather("Krasnoyarsk","en")
    }
}