package com.example.forecastmvvm.ui.weather.future.detail

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.example.forecastmvvm.R
import kotlinx.android.synthetic.main.current_weather_fragment.*

class FutureDetailWeatherFragment : Fragment() {

    companion object {
        fun newInstance() = FutureDetailWeatherFragment()
    }

    private lateinit var viewModel: FutureDetailWeatherViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.future_detail_weather_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(FutureDetailWeatherViewModel::class.java)
        // TODO: Use the ViewModel
    }

    private fun bindUI()=run {

/*
        val futureWeather = viewModel.weather.await()
            futureWeather.observe(this@FutureDetailWeatherFragment,Observer{
                if(it==null) return@Observer
                textView_condition.text = it.toString()

                Log.d("FutureWeather",it.toString())
            })
*/
        }






}



