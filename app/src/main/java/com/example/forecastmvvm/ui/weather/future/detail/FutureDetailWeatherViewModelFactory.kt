package com.example.forecastmvvm.ui.weather.future.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.forecastmvvm.data.repository.ForecastRepository
import com.resocoder.forecastmvvm.data.provider.UnitProvider

class FutureDetailWeatherViewModelFactory(

    private val forecastRepository: ForecastRepository,
    private val unitProvider: UnitProvider
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FutureDetailWeatherViewModel::class.java)) {
            return FutureDetailWeatherViewModel( forecastRepository, unitProvider) as T
    }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}


