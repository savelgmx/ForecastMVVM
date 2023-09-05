package com.example.forecastmvvm.ui.weather.future.detail

import androidx.lifecycle.ViewModel
import com.example.forecastmvvm.data.network.response.forecast.Daily
import com.example.forecastmvvm.data.repository.ForecastRepository
import com.resocoder.forecastmvvm.data.provider.UnitProvider


class FutureDetailWeatherViewModel : ViewModel() {
    // ViewModel logic here
    var selectedDaily: Daily? = null
        private set

    fun setSelectedDaily(daily: Daily) {
        selectedDaily = daily
    }
}

