package com.example.forecastmvvm.ui.base

import androidx.lifecycle.ViewModel
import com.example.forecastmvvm.data.repository.ForecastRepository
import com.example.forecastmvvm.internal.UnitSystem
import com.resocoder.forecastmvvm.data.provider.UnitProvider
import com.example.forecastmvvm.internal.lazyDeffered



abstract class WeatherViewModel(
    private val forecastRepository: ForecastRepository,
/*
    private val latitude:String,
    private val longitude:String,
*/
    unitProvider: UnitProvider
) : ViewModel() {

    private val unitSystem = unitProvider.getUnitSystem()

    val isMetricUnit: Boolean
        get() = unitSystem == UnitSystem.METRIC

 /*   val weatherLocation by lazyDeffered {
        forecastRepository.getFutureWeather(latitude,longitude)
    }
*/

}