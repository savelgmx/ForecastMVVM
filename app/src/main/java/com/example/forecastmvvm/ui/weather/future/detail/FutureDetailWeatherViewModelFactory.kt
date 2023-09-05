package com.example.forecastmvvm.ui.weather.future.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.forecastmvvm.data.network.response.forecast.Daily
import com.example.forecastmvvm.data.repository.ForecastRepository
import com.resocoder.forecastmvvm.data.provider.UnitProvider

class FutureDetailWeatherViewModelFactory : ViewModelProvider.NewInstanceFactory() {
    private lateinit var daily: Daily

    fun setDaily(daily: Daily) {
        this.daily = daily
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T
    {
        return FutureDetailWeatherViewModel().apply {
            setSelectedDaily(daily)
        } as T
    }
}


