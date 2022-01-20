package com.example.forecastmvvm.ui.weather.current

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.forecastmvvm.data.repository.ForecastRepository

/*
Actually,preservation of ViewModel is a job for ViewModelProvider
We only pass a factory into provider
As usual factories creates new instances of objects

 */
class CurrentWeatherViewModelFactory(
    private val forecastRepository: ForecastRepository,
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return CurrentWeatherViewModel(forecastRepository) as T
    }
}
