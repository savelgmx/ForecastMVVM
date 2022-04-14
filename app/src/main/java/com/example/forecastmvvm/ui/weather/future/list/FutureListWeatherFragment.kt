package com.example.forecastmvvm.ui.weather.future.list

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.example.forecastmvvm.R
import com.example.forecastmvvm.data.network.ConnectivityInterceptorImpl
import com.example.forecastmvvm.data.network.OpenWeatherApiService
import com.example.forecastmvvm.data.network.WeatherNetworkDataSourceImpl
import kotlinx.android.synthetic.main.current_weather_fragment.*

class FutureListWeatherFragment : Fragment() {

    companion object {
        fun newInstance() = FutureListWeatherFragment()
    }

    private lateinit var viewModel: FutureListWeatherViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.future_list_weather_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(FutureListWeatherViewModel::class.java)
        // TODO: Use the ViewModel
        callAPI()
    }
    private fun callAPI(){
        val iconurl = "http://openweathermap.org/img/w/"

/*
        "lon": 92.7917,
        "lat": 56.0097
*/

        val apiServiceOne = OpenWeatherApiService(ConnectivityInterceptorImpl(this.context!!))
        val weatherNetworkDataSource = WeatherNetworkDataSourceImpl(apiServiceOne)
        weatherNetworkDataSource.downloadedFutureWeather.observe(viewLifecycleOwner,
            Observer {
                textView_condition.text = it.toString()
            })

        val futureWeatherResponse = apiServiceOne.getForecastweather(
            "92.7917",
            "56.0097",
            "current,hourly" ,
            "metric"
        )
        Log.d("FutureWaetherResponse",futureWeatherResponse.toString())
        textView_condition.text = futureWeatherResponse.toString()
    }

}