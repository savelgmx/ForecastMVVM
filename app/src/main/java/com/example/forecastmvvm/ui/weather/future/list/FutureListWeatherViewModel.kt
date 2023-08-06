package com.example.forecastmvvm.ui.weather.future.list

import androidx.lifecycle.viewModelScope
import com.example.forecastmvvm.data.repository.ForecastRepository
import com.example.forecastmvvm.ui.base.WeatherViewModel
import com.resocoder.forecastmvvm.data.provider.UnitProvider
import com.example.forecastmvvm.internal.lazyDeffered

class FutureListWeatherViewModel(
    private val forecastRepository: ForecastRepository,
    private val latitude:String,
    private val longitude:String,
    unitProvider: UnitProvider
) : WeatherViewModel(forecastRepository, unitProvider) {

    val weatherLocation by lazyDeffered(viewModelScope) {
        forecastRepository.getFutureWeather(latitude, longitude)
    }

}

