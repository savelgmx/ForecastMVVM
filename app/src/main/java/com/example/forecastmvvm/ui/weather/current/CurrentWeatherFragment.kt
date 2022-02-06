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
import com.example.forecastmvvm.data.network.WeatherNetworkDataSourceImpl
import com.example.forecastmvvm.data.network.WeatherstackApiService
import com.example.forecastmvvm.ui.base.ScopedFragment
import com.resocoder.forecastmvvm.internal.glide.GlideApp
import kotlinx.android.synthetic.main.current_weather_fragment.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance


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

        val currentWeather = viewModel.weather.await()

        currentWeather.observe(this@CurrentWeatherFragment, Observer {
            if (it==null) return@Observer
           // textView.text = it.toString() no more present
            Log.d("UnitCurrentWeatherEntry",it.toString())

        })

    }


    private fun oldBindUI() {
       val apiServiceOne = WeatherstackApiService()
       // val apiServiceOne = WeatherstackApiService(ConnectivityInterceptorImpl(this.context!!))
        val weatherNetworkDataSource = WeatherNetworkDataSourceImpl(apiServiceOne)
        weatherNetworkDataSource.downloadedCurrentWeather.observe(viewLifecycleOwner,
            Observer {
                //textView.text = it.toString() not present anymore


                group_loading.visibility= View.GONE
            })

        GlobalScope.launch(Dispatchers.Main) {
            val currentWeatherResponse = apiServiceOne.getCurrentWeather("Krasnoyarsk") .await()
           // textView.text = currentWeatherResponse.toString()
            Log.d("CurrentWeatherresponse",currentWeatherResponse.toString())

            group_loading.visibility =View.GONE
            weatherNetworkDataSource.fetchCurrentWeather("Krasnoyarsk", "en")
            //===========================
            updateLocation(currentWeatherResponse.weatherLocation.name)
            updateDateToToday()
            updateTemperatures(currentWeatherResponse.currentWeatherEntry.temperature,
                currentWeatherResponse.currentWeatherEntry.temperature)
            updateCondition(currentWeatherResponse.currentWeatherEntry.weatherDescriptions.toString())
            updatePrecipitation(currentWeatherResponse.currentWeatherEntry.precip)
            updateWind(currentWeatherResponse.currentWeatherEntry.windDir,
                currentWeatherResponse.currentWeatherEntry.windSpeed)
            updateVisibility(currentWeatherResponse.currentWeatherEntry.visibility)
            //==============================================

            GlideApp.with(this@CurrentWeatherFragment)
                .load(
                    "${currentWeatherResponse.currentWeatherEntry.weatherIcons[0]}"
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

    private fun updateDateToToday() {
        (activity as? AppCompatActivity)?.supportActionBar?.subtitle = "Today"
    }

    //currentWeatherResponse.currentWeatherEntry.temperature

    private fun updateTemperatures(temperature: Int, feelsLike: Int) {
        val unitAbbreviation = chooseLocalizedUnitAbbreviation("°C", "°F")
        textView_temperature.text = "$temperature$unitAbbreviation"
        textView_feels_like_temperature.text = "Feels like $feelsLike$unitAbbreviation"
    }

    private fun updateCondition(condition: String) {
        textView_condition.text = condition
    }
  //currentWeatherResponse.currentWeatherEntry.precip
    private fun updatePrecipitation(precipitationVolume: Int) {
        val unitAbbreviation = chooseLocalizedUnitAbbreviation("mm", "in")
        textView_precipitation.text = "Preciptiation: $precipitationVolume $unitAbbreviation"
    }

    private fun updateWind(windDirection: String, windSpeed: Int) {
        val unitAbbreviation = chooseLocalizedUnitAbbreviation("kph", "mph")
        textView_wind.text = "Wind: $windDirection, $windSpeed $unitAbbreviation"
    }

    private fun updateVisibility(visibilityDistance: Int) {
        val unitAbbreviation = chooseLocalizedUnitAbbreviation("km", "mi.")
        textView_visibility.text = "Visibility: $visibilityDistance $unitAbbreviation"
    }


}