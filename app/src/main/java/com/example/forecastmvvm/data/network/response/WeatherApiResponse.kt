package com.example.forecastmvvm.data.network.response

import androidx.lifecycle.Observer
import com.example.forecastmvvm.data.network.ConnectivityInterceptorImpl
import com.example.forecastmvvm.data.network.OpenWeatherApiService
import com.example.forecastmvvm.data.network.WeatherNetworkDataSourceImpl

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware

/*Desigend for right response on API Query

 */

class WeatherApiResponse(override val kodein: Kodein) :KodeinAware {


    public fun oldBindUI() {
          val apiServiceOne = OpenWeatherApiService()
       // val apiServiceOne = WeatherstackApiService(ConnectivityInterceptorImpl(this@ForecastApplication.applicationContext!!))
        val weatherNetworkDataSource = WeatherNetworkDataSourceImpl(apiServiceOne)

        GlobalScope.launch(Dispatchers.Main) {
            val currentWeatherResponse = apiServiceOne.getCurrentWeather("Krasnoyarsk","metric").await()
         //   textView.text = currentWeatherResponse.toString()
            weatherNetworkDataSource.fetchCurrentWeather("Krasnoyarsk", "en")

        }
    }

}