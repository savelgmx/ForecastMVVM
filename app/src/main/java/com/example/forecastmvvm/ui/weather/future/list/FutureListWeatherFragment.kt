package com.example.forecastmvvm.ui.weather.future.list

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.forecastmvvm.R
import com.example.forecastmvvm.data.network.ConnectivityInterceptorImpl
import com.example.forecastmvvm.data.network.OpenWeatherApiService
import com.example.forecastmvvm.data.network.WeatherNetworkDataSourceImpl
import com.example.forecastmvvm.data.network.response.forecast.FutureWeatherResponse
import com.example.forecastmvvm.ui.base.ScopedFragment
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import kotlinx.android.synthetic.main.current_weather_fragment.*
import kotlinx.android.synthetic.main.current_weather_fragment.group_loading
import kotlinx.android.synthetic.main.current_weather_fragment.textView_condition
import kotlinx.android.synthetic.main.current_weather_fragment.textView_temperature
import kotlinx.android.synthetic.main.future_list_weather_fragment.*
import kotlinx.android.synthetic.main.item_future_weather.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.M
import org.kodein.di.generic.instance



class FutureListWeatherFragment() : ScopedFragment(), KodeinAware {

    override val kodein by closestKodein()
    private val viewModelFactory:FutureListWeatherViewModelFactory by instance(arg=M("latitude","longitude"))

//    private val futureWeatherResponse:FutureWeatherResponse

    private lateinit var viewModel: FutureListWeatherViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.future_list_weather_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = ViewModelProviders.of(this,viewModelFactory)
            .get(FutureListWeatherViewModel::class.java)
//        viewModel = ViewModelProvider(this).get(FutureListWeatherViewModel::class.java)
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
                group_loading.visibility = View.GONE
            })

        GlobalScope.launch(Dispatchers.Main){

            val futureWeatherResponse = apiServiceOne.getForecastweather(
                "92.7917",
                "56.0097",
                "current,hourly" ,
                "metric"
            ).await()

            Log.d("FutureWeatherResponse",futureWeatherResponse.toString())
//            textView_condition.text = futureWeatherResponse.toString()
            val range = futureWeatherResponse.daily.size

            for (i in 0 until range) {

/*
                textView_temperature.text = futureWeatherResponse.daily[i].temp.day.toString()
                textView_date.text = futureWeatherResponse.daily[i].dt.toString()
                textView_condition.text = futureWeatherResponse.daily[i].toString()
*/

                Log.d("t-FutureWeatherResponse",futureWeatherResponse.daily[i].temp.day.toString())
                Log.d("d-FutureWeatherResponse",futureWeatherResponse.daily[i].dt.toString())
                Log.d("c-FutureWeatherResponse",futureWeatherResponse.daily[i].toString())


            }


        }

    }

    private fun List<FutureWeatherResponse>.toFutureWeatherItems() : List<FutureWeatherItem> {
        return this.map {
            FutureWeatherItem(it)
        }
    }

    private fun initRecyclerView(items: List<FutureWeatherItem>) {
        val groupAdapter = GroupAdapter<ViewHolder>().apply {
            addAll(items)
        }

        recyclerView.apply {
            layoutManager = LinearLayoutManager(this@FutureListWeatherFragment.context)
            adapter = groupAdapter
        }

        groupAdapter.setOnItemClickListener { item, view ->
            (item as? FutureWeatherItem)?.let {
               // showWeatherDetail(it.weatherEntry.date, view)
            }
        }
    }

}

