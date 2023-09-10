package com.example.forecastmvvm.ui.weather.future.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.forecastmvvm.data.provider.DailyObjectProvider

class FutureDetailWeatherViewModelFactory(
    private val dailyObjectProvider: DailyObjectProvider
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FutureDetailWeatherViewModel::class.java)) {
            return FutureDetailWeatherViewModel(dailyObjectProvider) as T
    }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}


