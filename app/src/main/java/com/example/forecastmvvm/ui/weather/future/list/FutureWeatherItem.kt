package com.example.forecastmvvm.ui.weather.future.list

import com.example.forecastmvvm.R
import com.example.forecastmvvm.data.network.response.forecast.Daily
import com.example.forecastmvvm.internal.WeatherUtils
import com.resocoder.forecastmvvm.internal.glide.GlideApp
import com.xwray.groupie.kotlinandroidextensions.Item
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import kotlinx.android.synthetic.main.current_weather_fragment.*
import kotlinx.android.synthetic.main.item_future_weather.*
import kotlinx.android.synthetic.main.item_future_weather.imageView_condition_icon
import kotlinx.android.synthetic.main.item_future_weather.textView_condition
import kotlinx.android.synthetic.main.item_future_weather.textView_temperature


class FutureWeatherItem(
    val dailyWeather: Daily
) : Item() {
    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.apply {
            textView_condition.text = dailyWeather.weather[0].description
            textView_date.text=WeatherUtils.formatDateSubtitle(dailyWeather.dt)
            textView_temperature.text=WeatherUtils.updateTemperature(dailyWeather.temp.day.toInt())

            updateConditionImage(dailyWeather.weather?.get(0)?.icon)
        }
    }

    override fun getLayout() = R.layout.item_future_weather
    private fun ViewHolder.updateConditionImage(icon: String) {
        val iconurl = "http://openweathermap.org/img/w/"
        GlideApp.with(this.containerView)
            .load("$iconurl${icon}.png")
            .into(imageView_condition_icon)
    }

}
