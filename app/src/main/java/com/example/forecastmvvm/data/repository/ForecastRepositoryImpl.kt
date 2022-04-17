package com.example.forecastmvvm.data.repository

import androidx.lifecycle.LiveData
import com.example.forecastmvvm.data.db.CityDao
import com.example.forecastmvvm.data.db.entity.CityModel
import com.example.forecastmvvm.data.db.entity.ForecastCityModel
import com.example.forecastmvvm.data.network.WeatherNetworkDataSource
import com.example.forecastmvvm.data.network.response.OpenWeatherResponse
import com.example.forecastmvvm.data.provider.LocationProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.threeten.bp.ZonedDateTime

import java.util.*

class ForecastRepositoryImpl(
    private val cityDao: CityDao,
    private val weatherNetworkDataSource: WeatherNetworkDataSource,
    private val locationProvider: LocationProvider
) :ForecastRepository{
    init {
        weatherNetworkDataSource.downloadedCurrentWeather.observeForever {newCurrentWeather ->
            persistFetchedCurrentWeather(newCurrentWeather)
        }
    }


    override suspend fun getCurrentWeather(metric: Boolean): List<CityModel> {
        return withContext(Dispatchers.IO) {
            initWeatherData()
            return@withContext if (metric) cityDao.getAllCities()
            else cityDao.getAllCities()

        }
    }

    override suspend fun getFutureWeather(latitude:Double, longitude:Double): LiveData<ForecastCityModel> {
        TODO("Not yet implemented")
    }

    private fun persistFetchedCurrentWeather(fetchedWeather: OpenWeatherResponse?) {
        GlobalScope.launch(Dispatchers.IO) {
            if (fetchedWeather != null) {
/*
                if (fetchedWeather.currentWeatherEntry != null) {
                 //   if (fetchedWeather != null) {
                        currentWeatherDao.upsert(fetchedWeather.currentWeatherEntry)
                         weatherLocationDao.upsert(fetchedWeather.weatherLocation)

                  //  }
                } else { Log.d("fetchedWeather","currenWeatherEntry=null")}
*/
            }
        }

    }

    private suspend fun initWeatherData(){
 //       val lastWeatherLocation = weatherLocationDao.getLocation().value

   //     if (lastWeatherLocation == null
     //       || locationProvider.hasLocationChanged(lastWeatherLocation)) {
            fetchCurrentWeather()
            return
       // }

        //    if (isFetchCurrentNeeded(lastWeatherLocation.zonedDateTime))
          //      fetchCurrentWeather()
        }



    private suspend fun fetchCurrentWeather(){
        weatherNetworkDataSource.fetchCurrentWeather(
            locationProvider.getPreferredLocationString(),
            Locale.getDefault().language
        )


    }

    private fun isFetchCurrentNeeded(lastFetchTime: ZonedDateTime): Boolean {
        val thirtyMinutesAgo =ZonedDateTime.now().minusMinutes(30)
        return lastFetchTime.isBefore(thirtyMinutesAgo)
    }

}

