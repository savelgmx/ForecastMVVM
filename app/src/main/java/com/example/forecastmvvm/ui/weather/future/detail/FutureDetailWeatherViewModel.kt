package com.example.forecastmvvm.ui.weather.future.detail

import com.example.forecastmvvm.data.network.response.forecast.Daily
import com.example.forecastmvvm.data.repository.ForecastRepository
import com.example.forecastmvvm.internal.WeatherUtils
import com.example.forecastmvvm.ui.base.WeatherViewModel
import com.resocoder.forecastmvvm.data.provider.UnitProvider


class FutureDetailWeatherViewModel(
    private val forecastRepository: ForecastRepository,
    private val unitProvider: UnitProvider
) : WeatherViewModel(forecastRepository, unitProvider) {

    val selectedDaily: Daily?
        get() = WeatherUtils.getDailyObject()
}
