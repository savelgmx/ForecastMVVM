package com.example.forecastmvvm.ui.weather.current

import androidx.lifecycle.ViewModel
import com.example.forecastmvvm.data.repository.ForecastRepository
import com.example.forecastmvvm.internal.UnitSystem
import com.example.forecastmvvm.internal.lazyDeffered

class CurrentWeatherViewModel (
    private val forecastRepository: ForecastRepository
): ViewModel( ) {
    private val unitSystem=UnitSystem.METRIC


    val isMetric:Boolean
        get() = unitSystem==UnitSystem.METRIC

    val weather by lazyDeffered {
        forecastRepository.getCurrentWeather(isMetric)
    }

}

/*val apiService = WeatherstackApiService()
val apiService = WeatherstackApiService(ConnectivityInterceptorImpl(this.context!!))
val weatherNetworkDataSource = WeatherNetworkDataSourceImpl(apiService)
weatherNetworkDataSource.downloadedCurrentWeather.observe(this,
Observer {
    textView.text= it.toString()
})

GlobalScope.launch(Dispatchers.Main) {
    val currentWeatherResponse = apiService.getCurrentWeather("Krasnoyarsk").await()
    textView.text= currentWeatherResponse.toString()
    weatherNetworkDataSource.fetchCurrentWeather("Krasnoyarsk","en")*/
