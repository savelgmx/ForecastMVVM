package com.example.forecastmvvm.ui.weather.future.detail

import androidx.lifecycle.ViewModel
import com.example.forecastmvvm.data.network.response.forecast.Daily


class FutureDetailWeatherViewModel(daily: Daily) : ViewModel() {
    // ViewModel logic here
    lateinit var selectedDaily: Daily

    fun putSelectedDaily(daily: Daily) {
        selectedDaily = daily
    }
    fun retrieveSelectedDaily(): Daily {
        return selectedDaily
    }
}

