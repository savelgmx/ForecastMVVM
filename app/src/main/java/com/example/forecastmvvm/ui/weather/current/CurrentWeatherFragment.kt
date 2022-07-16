
package com.example.forecastmvvm.ui.weather.current

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.forecastmvvm.R
import com.example.forecastmvvm.domain.api.ConnectivityInterceptorImpl
import com.example.forecastmvvm.data.network.WeatherNetworkDataSourceImpl
import com.example.forecastmvvm.domain.api.OpenWeatherApiService

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



/*
    companion object{fun newInstance() = CurrentWeatherFragment()}
*/

    private lateinit var viewModel: CurrentWeatherViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.current_weather_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(CurrentWeatherViewModel::class.java)

//        viewModel = ViewModelProvider(this).get(CurrentWeatherViewModel::class.java)
        bindUI()

        oldBindUI()

    }


    @SuppressLint("FragmentLiveDataObserve")
    private fun bindUI()=launch{

/*        val currentWeather = viewModel.weather.await()

        currentWeather.observe(this@CurrentWeatherFragment, Observer {
            if (it==null) return@Observer
           // textView.text = it.toString() no more present
            Log.d("UnitCurrentWeatherEntry",it.toString())

        })*/

    }


    private fun oldBindUI() {

        val iconurl = "http://openweathermap.org/img/w/"

        val apiServiceOne = OpenWeatherApiService(ConnectivityInterceptorImpl(this.context!!))
        // val apiServiceOne = WeatherstackApiService(ConnectivityInterceptorImpl(this.context!!))
        val weatherNetworkDataSource = WeatherNetworkDataSourceImpl(apiServiceOne)
        weatherNetworkDataSource.downloadedCurrentWeather.observe(viewLifecycleOwner,
            Observer {
                //textView.text = it.toString() not present anymore


                group_loading.visibility= View.GONE
            })

        GlobalScope.launch(Dispatchers.Main) {
            val currentWeatherResponse = apiServiceOne.getCurrentWeather("Krasnoyarsk","metric") .await()
            Log.d("CurrentWeatherresponse",currentWeatherResponse.toString())
            group_loading.visibility =View.GONE
            weatherNetworkDataSource.fetchCurrentWeather("Krasnoyarsk", "metric")
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
        val simpleDateFormat = SimpleDateFormat("dd MMMM yyyy, HH:mm:ss", Locale.ENGLISH)
        var today:String="Today"
        if (dt != null) {
            today=simpleDateFormat.format(dt * 1000L)
        }

        (activity as? AppCompatActivity)?.supportActionBar?.subtitle = today //"Today"
    }


    private fun updateTemperatures(temperature: Int, feelsLike: Int) {
        val unitAbbreviation = chooseLocalizedUnitAbbreviation("°C", "°F")
        textView_temperature.text = "$temperature$unitAbbreviation"
        textView_feels_like_temperature.text = "Feels like $feelsLike$unitAbbreviation"
    }

    private fun updateCondition(condition: String) {
        textView_condition.text = condition
    }

    private fun updatePressure(pressureValue: Int) {
        val unitAbbreviation = chooseLocalizedUnitAbbreviation("mm", "in")
        textView_pressure.text = "Pressure: $pressureValue $unitAbbreviation"
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

    private fun updateVisibility(visibilityDistance: Int) {
        val unitAbbreviation = chooseLocalizedUnitAbbreviation("m", "mi.")
        textView_visibility.text = "Visibility: $visibilityDistance $unitAbbreviation"
    }


}