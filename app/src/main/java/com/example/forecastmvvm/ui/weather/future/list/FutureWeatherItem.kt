package com.example.forecastmvvm.ui.weather.future.list

import android.util.Log
import com.example.forecastmvvm.R
import com.example.forecastmvvm.data.network.response.forecast.Daily
import com.example.forecastmvvm.data.network.response.forecast.FutureWeatherResponse
import com.resocoder.forecastmvvm.internal.glide.GlideApp
import com.xwray.groupie.kotlinandroidextensions.Item
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import kotlinx.android.synthetic.main.current_weather_fragment.*
import kotlinx.android.synthetic.main.item_future_weather.*
import kotlinx.android.synthetic.main.item_future_weather.imageView_condition_icon
import kotlinx.android.synthetic.main.item_future_weather.textView_condition
import kotlinx.android.synthetic.main.item_future_weather.textView_temperature
import org.threeten.bp.format.DateTimeFormatter
import org.threeten.bp.format.FormatStyle


class FutureWeatherItem(
    val dailyWeather: Daily
) : Item() {
    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.apply {
            textView_condition.text = dailyWeather.weather[0].description

            updateDate(dailyWeather.dt.toString())
            updateTemperature(dailyWeather.temp.day.toString())
            updateConditionImage(dailyWeather.weather?.get(0)?.icon)
        }
    }

    override fun getLayout() = R.layout.item_future_weather

    private fun ViewHolder.updateDate(toString: String) {
        val dtFormatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM)
        textView_date.text = toString.format(dtFormatter)
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
