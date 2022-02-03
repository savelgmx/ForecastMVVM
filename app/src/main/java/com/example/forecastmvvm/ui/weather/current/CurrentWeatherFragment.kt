package com.example.forecastmvvm.ui.weather.current

import android.annotation.SuppressLint
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.forecastmvvm.R
import com.example.forecastmvvm.data.db.unitlocalized.current.UnitSpecificCurrentWeatherEntry
import com.example.forecastmvvm.data.network.ConnectivityInterceptorImpl
import com.example.forecastmvvm.data.network.WeatherNetworkDataSourceImpl
import com.example.forecastmvvm.data.network.WeatherstackApiService
import com.example.forecastmvvm.ui.base.ScopedFragment
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

    //  oldBindUI()

    }


    @SuppressLint("FragmentLiveDataObserve")
    private fun bindUI()=launch{

        val currentWeather = viewModel.weather.await()

        currentWeather.observe(this@CurrentWeatherFragment, Observer {
            if (it==null) return@Observer
            textView.text = it.toString()
        })

    }


    private fun oldBindUI() {
      //  val apiServiceOne = WeatherstackApiService()
        val apiServiceOne = WeatherstackApiService(ConnectivityInterceptorImpl(this.context!!))
        val weatherNetworkDataSource = WeatherNetworkDataSourceImpl(apiServiceOne)
        weatherNetworkDataSource.downloadedCurrentWeather.observeForever(viewLifecycleOwner,
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