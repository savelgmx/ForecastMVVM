package com.example.forecastmvvm.data.network.response

import androidx.lifecycle.Observer
import com.example.forecastmvvm.data.network.ConnectivityInterceptorImpl
import com.example.forecastmvvm.data.network.WeatherNetworkDataSourceImpl
import com.example.forecastmvvm.data.network.WeatherstackApiService
import kotlinx.android.synthetic.main.current_weather_fragment.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware

/*Desigend for right response on API Query

 */

class WeatherApiResponse(override val kodein: Kodein) :KodeinAware {

    huithis.applicationContext
    private fun oldBindUI() {
        //  val apiServiceOne = WeatherstackApiService()
        val apiServiceOne = WeatherstackApiService(ConnectivityInterceptorImpl(this.context!!))
        val weatherNetworkDataSource = WeatherNetworkDataSourceImpl(apiServiceOne)
        weatherNetworkDataSource.downloadedCurrentWeather.observe(viewLifecycleOwner,
            Observer {
                textView.text = it.toString()
            })

        GlobalScope.launch(Dispatchers.Main) {
            val currentWeatherResponse = apiServiceOne.getCurrentWeather("Krasnoyarsk").await()
            textView.text = currentWeatherResponse.toString()
            weatherNetworkDataSource.fetchCurrentWeather("Krasnoyarsk", "en")

        }
    }

}