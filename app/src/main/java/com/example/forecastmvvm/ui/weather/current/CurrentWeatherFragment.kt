
package com.example.forecastmvvm.ui.weather.current

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import com.example.forecastmvvm.R
import com.example.forecastmvvm.data.db.entity.CurrentMainEntry
import com.example.forecastmvvm.data.network.api.ConnectivityInterceptorImpl
import com.example.forecastmvvm.data.network.api.OpenWeatherApiService
import com.example.forecastmvvm.internal.WeatherUtils
import com.example.forecastmvvm.ui.base.ScopedFragment
import com.resocoder.forecastmvvm.internal.glide.GlideApp
import kotlinx.android.synthetic.main.current_weather_fragment.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance
import java.text.SimpleDateFormat
import java.util.*


class CurrentWeatherFragment() : ScopedFragment(),KodeinAware {

    override val kodein by closestKodein()
    private val viewModelFactory: CurrentWeatherViewModelFactory by instance()

    // Your ViewModel instance
    private lateinit var currentWeatherViewModel: CurrentWeatherViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.current_weather_fragment, container, false)

        // Initialize ViewModel
        currentWeatherViewModel = ViewModelProvider(this, viewModelFactory)
            .get(CurrentWeatherViewModel::class.java)

        currentWeatherViewModel.fetchWeather()
        // Observe the weather LiveData for changes
        currentWeatherViewModel.weather.observe(viewLifecycleOwner) { currentWeatherEntry ->
            // Update UI with weather data
            if (currentWeatherEntry != null) {
                // Use the data to update UI components Call the bindUI method
                bindUI(currentWeatherEntry)

            } else {
                // Handle the case where the data is null or an error occurred
                // You might want to show an error message or handle it accordingly
            }
        }

        return view
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        //  bindUI()
        oldBindUI()
    }

    private fun bindUI(currentMainEntry: CurrentMainEntry) {

        val iconurl = "http://openweathermap.org/img/w/"
        // Update UI with weather data



        if (currentMainEntry != null) {

            Log.d("CurrentWeatherResponse", "CurrentWeatherFragment-bindUI: $currentMainEntry")
           group_loading.visibility = View.GONE

/*
            WeatherUtils.setLatitude(currentMainEntry.coord.lat)
            WeatherUtils.setLongitude(currentMainEntry.coord.lon)

            //===========================
            updateLocation(currentMainEntry.name)
            updateDateToToday(currentMainEntry.dt)
            updateTemperatures(
                currentMainEntry.main.temp.toInt(),
                currentMainEntry.main.feelsLike.toInt()
            )
            updateCondition(currentMainEntry.weather[0].description)
            updatePressure(currentMainEntry.main.pressure)
            updateWind(currentMainEntry.wind.deg.toString(), currentMainEntry.wind.speed.toInt())
            updateVisibility(currentMainEntry.visibility)
            //==============================================

            GlideApp.with(this@CurrentWeatherFragment)
                .load("${iconurl}${currentMainEntry.weather[0].icon}.png")
                .into(imageView_condition_icon)
*/
           //===============================================
        } else {
            // Handle the case where the data is null or an error occurred
            // You might want to show an error message or handle it accordingly
        }
    }


    private fun oldBindUI() {

        val iconurl = "http://openweathermap.org/img/w/"

        val apiServiceOne = OpenWeatherApiService(ConnectivityInterceptorImpl(this.requireContext()))

        GlobalScope.launch(Dispatchers.Main) {
            val currentWeatherResponse = apiServiceOne.getCurrentWeather("Krasnoyarsk","metric",Locale.getDefault().language) .await()
            //    Log.d("CurrentWeatherresponse",currentWeatherResponse.toString())
            group_loading.visibility =View.GONE
            currentWeatherResponse.coord?.let { WeatherUtils.setLatitude(it.lat) }
            currentWeatherResponse.coord?.let { WeatherUtils.setLongitude(it.lon) }

            //===========================
            updateLocation(currentWeatherResponse.name.toString())
            updateDateToToday(currentWeatherResponse.dt)
            currentWeatherResponse.main?.temp?.let {
                updateTemperatures(
                    it.toInt(),
                    currentWeatherResponse.main.feelsLike.toInt())
            }
            updateCondition(currentWeatherResponse.weather?.get(0)?.description.toString())
            currentWeatherResponse.main?.let { updatePressure(it.pressure) }
            currentWeatherResponse.wind?.speed?.let {
                updateWind(
                    currentWeatherResponse.wind?.deg.toString(),
                    it.toInt())
            }
            currentWeatherResponse.visibility?.let { updateVisibility(it) }
            //==============================================

            GlideApp.with(this@CurrentWeatherFragment)
                .load(
                    "$iconurl${currentWeatherResponse.weather?.get(0)?.icon}"+".png"
                )
                .into(imageView_condition_icon)
            //===============================================
        }
    }

    private fun chooseLocalizedUnitAbbreviation(metric: String, imperial: String): String {
        // return if (viewModel.isMetricUnit) metric else imperial
        return metric
    }

    private fun updateLocation(location: String) {
        // currentWeatherResponse.location.name Update Location
        (activity as? AppCompatActivity)?.supportActionBar?.title = location
    }

    private fun updateDateToToday(dt: Int?) {
        //API returns date/time as a UnixEpoc integer timestamp
        //we must transform this with datetime format
        val simpleDateFormat = SimpleDateFormat("EEE dd MMMM yyyy", Locale.getDefault())
        var today:String="Today"
        if (dt != null) {
            today=simpleDateFormat.format(dt * 1000L)
        }

        (activity as? AppCompatActivity)?.supportActionBar?.subtitle = today //"Today"
    }


    private fun updateTemperatures(temperature: Int, feelsLike: Int) {
        val unitAbbreviation = chooseLocalizedUnitAbbreviation("°C", "°F")
        textView_temperature.text = "$temperature$unitAbbreviation"
        textView_feels_like_temperature.text = getString(R.string.feels_like)+":"+"$feelsLike$unitAbbreviation"
    }

    private fun updateCondition(condition: String) {
        textView_condition.text = condition
    }

    private fun updatePressure(pressureValue: Int) {
        val unitAbbreviation = chooseLocalizedUnitAbbreviation("mm", "in")
        textView_pressure.text = getString(R.string.pressure)+":"+"$pressureValue $unitAbbreviation"
    }

    private fun updateWind(windDirection: String, windSpeed: Int) {
        val unitAbbreviation = chooseLocalizedUnitAbbreviation("m/sec.", "mph")

        val wind=degToCompass((windDirection).toInt())

        textView_wind.text = getString(R.string.wind)+":"+" $wind , $windSpeed $unitAbbreviation"
    }

    private fun degToCompass(num: Int): String {
        val winDir = Math.floor((num / 22.5) + 0.5).toInt()
        val directions = resources.getStringArray(R.array.directions_array) // Load the array from resources
        return directions[(winDir % 16).toInt()]
    }

    private fun updateVisibility(visibilityDistance: Int) {
        val unitAbbreviation = chooseLocalizedUnitAbbreviation("m", "mi.")
        textView_visibility.text = getString(R.string.visibility)+":"+"$visibilityDistance $unitAbbreviation"
    }


}