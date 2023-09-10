package com.example.forecastmvvm.data.provider

import com.example.forecastmvvm.data.network.response.forecast.Daily

interface DailyObjectProvider {
        fun getDailyObject(): Daily?
        fun setDailyObject(daily: Daily)
}