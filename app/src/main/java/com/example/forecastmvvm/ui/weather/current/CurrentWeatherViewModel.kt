package com.example.forecastmvvm.ui.weather.current

import androidx.lifecycle.*
import com.example.forecastmvvm.data.db.entity.CurrentWeatherEntry
import com.example.forecastmvvm.data.repository.ForecastRepository
import com.example.forecastmvvm.internal.UnitSystem
import com.resocoder.forecastmvvm.data.provider.UnitProvider
import kotlinx.coroutines.launch


class CurrentWeatherViewModel (
    private val forecastRepository: ForecastRepository,
    unitProvider: UnitProvider
) : ViewModel() {
    private val unitSystem = unitProvider.getUnitSystem()

    private val isMetric: Boolean = unitProvider.getUnitSystem() == UnitSystem.METRIC

    // Using MutableLiveData to hold the weather data
    private val _weather = MutableLiveData<CurrentWeatherEntry>()

    // Exposing LiveData to be observed
    val weather: LiveData<CurrentWeatherEntry>
        get() = _weather

    init {
        fetchWeather()
    }

    private fun fetchWeather()= viewModelScope.launch{
        val currentWeather = forecastRepository.getCurrentWeather(isMetric)
        _weather.postValue(currentWeather.value) // Use postValue() to update LiveData from background thread
    }
}
