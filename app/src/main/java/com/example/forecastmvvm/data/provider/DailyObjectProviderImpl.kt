package com.example.forecastmvvm.data.provider

import com.example.forecastmvvm.data.network.response.forecast.Daily

class DailyObjectProviderImpl : DailyObjectProvider {
    private var daily: Daily? = null

    override fun getDailyObject(): Daily? {
        return daily
    }

    override fun setDailyObject(daily: Daily) {
        this.daily = daily
    }
}
