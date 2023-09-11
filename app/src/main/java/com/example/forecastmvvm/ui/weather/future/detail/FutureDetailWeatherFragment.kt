package com.example.forecastmvvm.ui.weather.future.detail

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.forecastmvvm.R
import com.example.forecastmvvm.data.network.response.forecast.Daily
import com.example.forecastmvvm.ui.base.ScopedFragment
import kotlinx.android.synthetic.main.current_weather_fragment.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.factory
import org.kodein.di.generic.instance


class FutureDetailWeatherFragment : ScopedFragment(), KodeinAware {

    override val kodein by closestKodein()

    private val ARG_SELECTED_DAY = "selected_day"
    private lateinit var viewModel: FutureDetailWeatherViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.future_detail_weather_fragment, container, false)
        }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val selectedDay = arguments?.getParcelable<Daily>(ARG_SELECTED_DAY)
        val viewModelFactory: FutureDetailWeatherViewModelFactory by instance()
        viewModel = ViewModelProvider(this, viewModelFactory)
            .get(FutureDetailWeatherViewModel::class.java)

        val currentSelectedDaily = viewModel.selectedDaily

        if (currentSelectedDaily != null) {
            populateUI(currentSelectedDaily)
        }

        selectedDay?.let {
            populateUI(it)
        }
    }

    private fun populateUI(selectedDay: Daily) {
        // Populate your UI elements with data from selectedDay
        textView_condition.text = selectedDay.weather[0].description
        // Populate more views as needed
    }
}
