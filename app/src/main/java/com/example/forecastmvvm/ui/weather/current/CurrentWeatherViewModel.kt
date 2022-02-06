package com.example.forecastmvvm.ui.weather.current

import androidx.lifecycle.ViewModel
import com.example.forecastmvvm.data.repository.ForecastRepository
import com.example.forecastmvvm.internal.UnitSystem
import com.example.forecastmvvm.internal.lazyDeffered
import com.resocoder.forecastmvvm.data.provider.UnitProvider

class CurrentWeatherViewModel (
    private val forecastRepository: ForecastRepository,
    unitProvider: UnitProvider
): ViewModel( ) {
    private val unitSystem = unitProvider.getUnitSystem()

    val isMetric:Boolean
        get() = unitSystem==UnitSystem.METRIC

    val weather by lazyDeffered {
        forecastRepository.getCurrentWeather(isMetric)
    }

}

