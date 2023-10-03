package com.example.forecastmvvm.ui.weather.future.detail

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.example.forecastmvvm.R
import com.example.forecastmvvm.data.network.response.forecast.Daily
import com.example.forecastmvvm.internal.WeatherUtils
import com.example.forecastmvvm.ui.base.ScopedFragment
import com.resocoder.forecastmvvm.internal.glide.GlideApp
import kotlinx.android.synthetic.main.current_weather_fragment.*
import kotlinx.android.synthetic.main.current_weather_fragment.group_loading
import kotlinx.android.synthetic.main.current_weather_fragment.imageView_condition_icon
import kotlinx.android.synthetic.main.current_weather_fragment.textView_condition
import kotlinx.android.synthetic.main.current_weather_fragment.textView_feels_like_temperature
import kotlinx.android.synthetic.main.current_weather_fragment.textView_pressure
import kotlinx.android.synthetic.main.current_weather_fragment.textView_temperature
import kotlinx.android.synthetic.main.current_weather_fragment.textView_wind
import kotlinx.android.synthetic.main.future_detail_weather_fragment.*
import kotlinx.android.synthetic.main.future_detail_weather_fragment.view.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.factory
import org.kodein.di.generic.instance
import java.text.SimpleDateFormat
import java.util.*


class FutureDetailWeatherFragment : ScopedFragment(), KodeinAware {

    override val kodein by closestKodein()

    private val ARG_SELECTED_DAY = "selected_day"

    private val viewModelFactoryInstanceFactory: ((Daily) -> FutureDetailWeatherViewModelFactory) by factory()

    private lateinit var viewModel: FutureDetailWeatherViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.future_detail_weather_fragment, container, false)
        }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

/*        val selectedDay = arguments?.getParcelable<Daily>(ARG_SELECTED_DAY)

        val viewModelFactory: FutureDetailWeatherViewModelFactory by instance()
        viewModel = ViewModelProvider(this, viewModelFactory)
            .get(FutureDetailWeatherViewModel::class.java)
*/

        val currentSelectedDaily = WeatherUtils.getDailyObject() //viewModel.getDailyObject()

        if (currentSelectedDaily != null) {

            updateDateToToday(currentSelectedDaily.dt)
            populateUI(currentSelectedDaily)

            group_loading.visibility =View.GONE
        }

    }

    private fun populateUI(selectedDay: Daily) {
        val iconurl = "http://openweathermap.org/img/w/"
        // Populate your UI elements with data from selectedDay

        textView_temp_day.text  =getString(R.string.day)+":"+WeatherUtils.updateTemperature(selectedDay.temp.day.toInt())
        textView_temp_night.text=getString(R.string.night)+":"+WeatherUtils.updateTemperature(selectedDay.temp.night.toInt())

        textView_condition.text = selectedDay.weather[0].description
        textView_temperature.text = WeatherUtils.updateTemperature(selectedDay.temp.day.toInt())
        textView_feels_like_temperature.text = getString(R.string.feels_like)+":"+WeatherUtils.updateTemperature(selectedDay.feelsLike.day.toInt())
        textView_pressure.text= getString(R.string.pressure)+":"+selectedDay.pressure.toString()+" mm."
        //==============================================

        GlideApp.with(this@FutureDetailWeatherFragment)
            .load(
                "$iconurl${selectedDay.weather[0].icon}"+".png"
            )
            .into(imageView_condition_icon)
        //===============================================
        // Populate more views as needed
        textView_wind.text=getString(R.string.wind)+":"+WeatherUtils.updateWind(selectedDay.windDeg.toString(),selectedDay.windSpeed.toInt())
        textView_sunrise.text=getString(R.string.sunrise)+":"+ WeatherUtils.updateTime(selectedDay.sunrise)
        textView_sunset.text=getString(R.string.sunset)+":"+ WeatherUtils.updateTime(selectedDay.sunset)

    }

    private fun updateDateToToday(dt: Int?) {
        //API returns date/time as a UnixEpoc integer timestamp
        //we must transform this with datetime format
        val simpleDateFormat = SimpleDateFormat("EEE dd MMMM yyyy, HH:mm:ss", Locale.getDefault())
        var today:String="Today"
        if (dt != null) {
            today=simpleDateFormat.format(dt * 1000L)
        }

        (activity as? AppCompatActivity)?.supportActionBar?.subtitle = today //"Today"
    }


}
