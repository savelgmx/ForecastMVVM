package com.example.forecastmvvm.ui.weather.future.list

import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModel
import com.example.forecastmvvm.data.network.ConnectivityInterceptorImpl
import com.example.forecastmvvm.data.network.OpenWeatherApiService
import com.example.forecastmvvm.data.network.WeatherNetworkDataSourceImpl
import com.example.forecastmvvm.data.repository.ForecastRepository
import com.example.forecastmvvm.ui.base.WeatherViewModel
import com.resocoder.forecastmvvm.data.provider.UnitProvider
import com.example.forecastmvvm.internal.lazyDeffered
import kotlinx.android.synthetic.main.current_weather_fragment.*
import kotlinx.android.synthetic.main.item_future_weather.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.threeten.bp.LocalDate

class FutureListWeatherViewModel(
    private val forecastRepository: ForecastRepository,
    private val latitude:String,
    private val longitude:String,
    unitProvider: UnitProvider
) : WeatherViewModel(forecastRepository,latitude, longitude, unitProvider) {

    val weather by lazyDeffered {
        forecastRepository.getFutureWeather(latitude,longitude )
    }

}

