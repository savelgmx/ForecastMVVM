package com.example.forecastmvvm.ui.weather.future.list

import androidx.lifecycle.ViewModel
import com.example.forecastmvvm.data.repository.ForecastRepository
import com.example.forecastmvvm.ui.base.WeatherViewModel
import com.resocoder.forecastmvvm.data.provider.UnitProvider
import com.example.forecastmvvm.internal.lazyDeffered
import org.threeten.bp.LocalDate

class FutureListWeatherViewModel(
    private val forecastRepository: ForecastRepository,
    private val latitude:Double,
    private val longitude:Double,
    unitProvider: UnitProvider
) : WeatherViewModel(forecastRepository,latitude, longitude, unitProvider) {

    val weatherEntries by lazyDeffered {
        forecastRepository.getFutureWeather(latitude,longitude )
    }
}

