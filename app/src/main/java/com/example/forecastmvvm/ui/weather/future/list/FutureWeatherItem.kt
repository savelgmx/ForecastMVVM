package com.example.forecastmvvm.ui.weather.future.list

import com.example.forecastmvvm.R
import com.example.forecastmvvm.data.network.response.forecast.Daily
import com.example.forecastmvvm.internal.WeatherUtils
import com.resocoder.forecastmvvm.internal.glide.GlideApp
import com.xwray.groupie.kotlinandroidextensions.Item
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import kotlinx.android.synthetic.main.item_future_weather.*


class FutureWeatherItem(
    val dailyWeather: Daily
) : Item() {
    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.apply {
            textView_condition.text = dailyWeather.weather[0].description
            updateDate(dailyWeather.dt)
            updateTemperature(dailyWeather.temp.day.toString())
            updateConditionImage(dailyWeather.weather?.get(0)?.icon)
        }
    }

    override fun getLayout() = R.layout.item_future_weather

    private fun ViewHolder.updateDate(dt: Int) {
        val formattedDate = WeatherUtils.formatDateSubtitle(dt)
        textView_date.text = formattedDate
    }

    private fun ViewHolder.updateTemperature(temp: String) {
        val unitAbbreviation = "°C"
        textView_temperature.text = "${temp}$unitAbbreviation"
    }

    private fun ViewHolder.updateConditionImage(icon: String) {
        val iconurl = "http://openweathermap.org/img/w/"
        GlideApp.with(this.containerView)
            .load("$iconurl${icon}.png")
            .into(imageView_condition_icon)
    }
}
