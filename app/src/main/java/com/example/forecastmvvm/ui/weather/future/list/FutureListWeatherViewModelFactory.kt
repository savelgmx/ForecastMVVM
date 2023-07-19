package com.example.forecastmvvm.ui.weather.future.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.forecastmvvm.data.repository.ForecastRepository
import com.resocoder.forecastmvvm.data.provider.UnitProvider

//we cannot create many instances of viewModel from inside of view ,that's why we need viewmodelfactory
// it's about saving state of current view model

class FutureListWeatherViewModelFactory(
    private val forecastRepository: ForecastRepository,
    private val latitude:String,
    private val longitude:String,
    private val unitProvider: UnitProvider
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return FutureListWeatherViewModel(
            forecastRepository,
            latitude,
            longitude,
            unitProvider
        ) as T
    }
}