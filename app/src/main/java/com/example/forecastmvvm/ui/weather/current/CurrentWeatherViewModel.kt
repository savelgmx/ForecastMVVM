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
    val weather: LiveData<CurrentWeatherEntry> get() = _weather
    private val _isDataFetched = MutableLiveData<Boolean>()
    val isDataFetched: LiveData<Boolean> get() = _isDataFetched

    init {
        // Initialize with false, indicating data hasn't been fetched yet
        _isDataFetched.value = false
    }

    fun fetchWeather() {
        viewModelScope.launch {
            try {
                val currentWeather = forecastRepository.getCurrentWeather(isMetric)
                _weather.postValue(currentWeather)

                // Set the state variable to true indicating data has been fetched
                _isDataFetched.postValue(true)
            } catch (e: Exception) {
                // Handle exceptions if any
                e.printStackTrace()
            }
        }
    }
}
