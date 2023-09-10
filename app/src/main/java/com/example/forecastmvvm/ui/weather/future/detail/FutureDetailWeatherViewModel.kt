package com.example.forecastmvvm.ui.weather.future.detail

import androidx.lifecycle.ViewModel
import com.example.forecastmvvm.data.network.response.forecast.Daily
import com.example.forecastmvvm.data.provider.DailyObjectProvider


class FutureDetailWeatherViewModel(private val dailyObjectProvider: DailyObjectProvider) : ViewModel() {
    // ViewModel logic here

    fun getDailyObject(): Daily? {
        return dailyObjectProvider.getDailyObject()
    }
}
