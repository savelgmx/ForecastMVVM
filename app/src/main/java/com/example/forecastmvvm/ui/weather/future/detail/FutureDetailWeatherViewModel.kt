package com.example.forecastmvvm.ui.weather.future.detail

import androidx.lifecycle.ViewModel
import com.example.forecastmvvm.data.network.response.forecast.Daily
import com.example.forecastmvvm.data.provider.DailyObjectProvider
import com.example.forecastmvvm.data.repository.ForecastRepository
import com.example.forecastmvvm.ui.base.WeatherViewModel
import com.resocoder.forecastmvvm.data.provider.UnitProvider


class FutureDetailWeatherViewModel(
    private val dailyObjectProvider: DailyObjectProvider,
    private val forecastRepository: ForecastRepository,
    private val unitProvider: UnitProvider
) : WeatherViewModel(forecastRepository, unitProvider) {

    val selectedDaily: Daily
        get() = dailyObjectProvider.getDailyObject()
}
