package com.example.forecastmvvm.ui.weather.future.detail

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProviders
import com.example.forecastmvvm.R
import com.example.forecastmvvm.data.network.response.forecast.Daily
import com.example.forecastmvvm.ui.base.ScopedFragment
import com.google.gson.Gson
import kotlinx.android.synthetic.main.current_weather_fragment.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.closestKodein
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.factory


class FutureDetailWeatherFragment : ScopedFragment(), KodeinAware {

    override val kodein by closestKodein()

    private val ARG_SELECTED_DAY = "selected_day"

  //  private var viewModel: FutureDetailWeatherViewModel by activityViewModels()
    private val viewModel: FutureDetailWeatherViewModel by activityViewModels<FutureDetailWeatherViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.future_detail_weather_fragment, container, false)
        }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val selectedDay = arguments?.getParcelable<Daily>(ARG_SELECTED_DAY)

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
