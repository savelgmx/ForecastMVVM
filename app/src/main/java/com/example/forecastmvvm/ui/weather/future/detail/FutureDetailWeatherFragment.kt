package com.example.forecastmvvm.ui.weather.future.detail

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.forecastmvvm.R
import com.example.forecastmvvm.data.network.response.forecast.Daily
import kotlinx.android.synthetic.main.current_weather_fragment.*

class FutureDetailWeatherFragment : Fragment() {

    companion object {
        private const val ARG_SELECTED_DAY = "selected_day"

        fun newInstance(selectedDay: Daily): FutureDetailWeatherFragment {
            val fragment = FutureDetailWeatherFragment()
            val args = Bundle().apply {
      //          putParcelable(ARG_SELECTED_DAY, selectedDay)
            }
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.future_detail_weather_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //val selectedDay = Daily //arguments?.getParcelable(ARG_SELECTED_DAY) as? Daily

    //    selectedDay?.let {
      //      populateUI(it)
        }
    }

    private fun populateUI(selectedDay: Daily) {
        // Populate your UI elements with data from selectedDay
      //  textView_condition.text = selectedDay.weather[0].description
  //      textView_max_temp.text = "Max Temp: ${selectedDay.temp.max} °C"
     //   textView_min_temp.text = "Min Temp: ${selectedDay.temp.min} °C"
        // Populate more views as needed
    }



