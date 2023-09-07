package com.example.forecastmvvm.ui.weather.future.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.forecastmvvm.data.network.response.forecast.Daily

class FutureDetailWeatherViewModelFactory(private val daily: Daily) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FutureDetailWeatherViewModel::class.java)) {
            return FutureDetailWeatherViewModel(daily) as T
    }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}


