package com.example.forecastmvvm.ui.weather.future.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.forecastmvvm.R
import com.example.forecastmvvm.data.network.api.ConnectivityInterceptorImpl
import com.example.forecastmvvm.data.network.api.OpenWeatherApiService
import com.example.forecastmvvm.data.network.WeatherNetworkDataSourceImpl

import com.example.forecastmvvm.internal.WeatherUtils
import com.example.forecastmvvm.ui.base.ScopedFragment
import com.example.forecastmvvm.ui.weather.future.detail.FutureDetailWeatherViewModel
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import kotlinx.android.synthetic.main.current_weather_fragment.group_loading
import kotlinx.android.synthetic.main.current_weather_fragment.textView_condition
import kotlinx.android.synthetic.main.future_list_weather_fragment.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.M
import org.kodein.di.generic.instance


class FutureListWeatherFragment() : ScopedFragment(), KodeinAware {

   private var lon = WeatherUtils.getLongitude().toString()
   private var lat = WeatherUtils.getLatitude().toString()
    override val kodein by closestKodein()

    /*
         "lon": 92.7917,
         "lat": 56.0097
 */

    private val viewModelFactory: FutureListWeatherViewModelFactory by instance(
        arg = M(
            lon,
            lat
        )
    )
    private lateinit var viewModel: FutureListWeatherViewModel
    private lateinit var futureDetailWeatherViewModel: FutureDetailWeatherViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.future_list_weather_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val units = "metric"
        viewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(FutureListWeatherViewModel::class.java)

        //     viewModel = ViewModelProvider(this).get(FutureListWeatherViewModel::class.java)
        // callViewModel()
        callAPI()

    }

    private fun callViewModel() = launch {
/*
        val futureWeatherEntries=viewModel.weather.await()

        futureWeatherEntries.observe(this@FutureListWeatherFragment, Observer { weatherEntries ->
            if (weatherEntries == null) return@Observer
            Log.d("t-weatherEntryResponse",weatherEntries.temp.toString())
            Log.d("d-weatherEntryResponse",weatherEntries.toString())

        //    initRecyclerView(weatherEntries.toFutureWeatherItems())

        })
*/

    }

    private fun callAPI() {
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

        GlobalScope.launch(Dispatchers.Main) {

/*            var lon = WeatherUtils.getLongitude().toString()
            var lat = WeatherUtils.getLatitude().toString()*/

            val futureWeatherResponse = apiServiceOne.getForecastweather(
                lon,
                lat,
                "current,hourly",
                "metric"
            ).await()

            if(group_loading!=null)  { group_loading.visibility =View.GONE}
            val futureWeatherItems = mutableListOf<FutureWeatherItem>()

            for (daily in futureWeatherResponse.daily) {
                futureWeatherItems.add(FutureWeatherItem(daily))
            }

            initRecyclerView(futureWeatherItems)


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

        // Inside your setOnClickListener in initRecyclerView
        groupAdapter.setOnItemClickListener { item, view ->
            (item as? FutureWeatherItem)?.let {
                val selectedDaily = it.dailyWeather

          //      dailyObjectProvider.setDailyObject(selectedDaily)

                WeatherUtils.setDailyObject(selectedDaily)

                val actionDetail = FutureListWeatherFragmentDirections.actionDetail(selectedDaily.toString())
                view.findNavController().navigate(actionDetail)
            }
        }
    }


}

