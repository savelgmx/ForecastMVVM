package com.example.forecastmvvm.ui.weather.future.detail

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.forecastmvvm.R
import com.example.forecastmvvm.data.network.response.forecast.Daily
import com.example.forecastmvvm.ui.base.ScopedFragment
import com.google.gson.Gson
import kotlinx.android.synthetic.main.current_weather_fragment.*

class FutureDetailWeatherFragment : ScopedFragment() {

    companion object {
        private const val ARG_SELECTED_DAY_JSON = "selected_day_json"

        fun newInstance(selectedDayJson: String): FutureDetailWeatherFragment {
            val fragment = FutureDetailWeatherFragment()
            val args = Bundle().apply {
                putString(ARG_SELECTED_DAY_JSON, selectedDayJson)
            }
            fragment.arguments = args
            return fragment
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val selectedDayJson = arguments?.getString(ARG_SELECTED_DAY_JSON)
        val selectedDay = Gson().fromJson(selectedDayJson, Daily::class.java)
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
