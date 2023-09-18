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
        textView_condition.text = selectedDay.weather[0].description
        textView_temperature.text = selectedDay.temp.day.toString()
        textView_feels_like_temperature.text = selectedDay.feelsLike.day.toString()
        textView_pressure.text= selectedDay.pressure.toString()
        //==============================================

        GlideApp.with(this@FutureDetailWeatherFragment)
            .load(
                "$iconurl${selectedDay.weather[0].icon}"+".png"
            )
            .into(imageView_condition_icon)
        //===============================================
        // Populate more views as needed
        updateWind(selectedDay.windDeg.toString(),selectedDay.windSpeed.toInt())


    }

    private fun updateDateToToday(dt: Int?) {
        //API returns date/time as a UnixEpoc integer timestamp
        //we must transform this with datetime format
        val simpleDateFormat = SimpleDateFormat("dd MMMM yyyy, HH:mm:ss", Locale.ENGLISH)
        var today:String="Today"
        if (dt != null) {
            today=simpleDateFormat.format(dt * 1000L)
        }

        (activity as? AppCompatActivity)?.supportActionBar?.subtitle = today //"Today"
    }
    private fun updateWind(windDirection: String, windSpeed: Int) {
        val unitAbbreviation = chooseLocalizedUnitAbbreviation("m/sec.", "mph")

        val wind=degToCompass((windDirection).toInt())

        textView_wind.text = "Wind: $wind , $windSpeed $unitAbbreviation"
    }


    private fun degToCompass(num:Int): String {
        var winDir = Math.floor((num / 22.5) + 0.5);
        var directions = listOf<String>("North", "North North East", "North East", "East North East",
            "East", "East South East", "South East", "South South East", "South",
            "South South West", "South West", "West South West", "West", "West North West",
            "North West", "North North West")
        return directions[(winDir % 16).toInt()]

    }

    private fun chooseLocalizedUnitAbbreviation(metric: String, imperial: String): String {
        // return if (viewModel.isMetricUnit) metric else imperial
        return metric
    }


    private fun updateLocation(location: String) {
        // currentWeatherResponse.location.name Update Location

        (activity as? AppCompatActivity)?.supportActionBar?.title = location
    }


}
