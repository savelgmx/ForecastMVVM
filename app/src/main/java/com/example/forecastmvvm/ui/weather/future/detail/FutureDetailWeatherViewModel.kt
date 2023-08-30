package com.example.forecastmvvm.ui.weather.future.detail

import androidx.lifecycle.ViewModel
import com.example.forecastmvvm.data.network.response.forecast.Daily
import com.example.forecastmvvm.data.repository.ForecastRepository
import com.resocoder.forecastmvvm.data.provider.UnitProvider

class FutureDetailWeatherViewModel(
    daily: Daily,
    forecastRepository: ForecastRepository,
    unitProvider: UnitProvider
) : ViewModel() {
    // TODO: Implement the ViewModel
    //val weather by lazyDeffered {
     //   forecastRepository.getFutureWeather(latitude:String, longitude:String)
   // }

}