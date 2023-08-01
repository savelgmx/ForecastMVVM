package com.example.forecastmvvm.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import com.example.forecastmvvm.data.ResultData
import com.example.forecastmvvm.data.db.CurrentWeatherDao
import com.example.forecastmvvm.data.db.ForecastCityDao
import com.example.forecastmvvm.data.db.FutureWeatherDao
import com.example.forecastmvvm.data.db.entity.CurrentWeatherEntry
import com.example.forecastmvvm.data.db.entity.ForecastCityModel
import com.example.forecastmvvm.data.network.WeatherNetworkDataSource
import com.example.forecastmvvm.data.network.response.current.CurrentWeatherResponse
import com.example.forecastmvvm.data.network.response.forecast.FutureWeatherResponse
import com.example.forecastmvvm.data.provider.LocationProvider
import com.resocoder.forecastmvvm.data.provider.UnitProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.threeten.bp.ZonedDateTime

private var longitude:Double = 0.0
private var latitude:Double = 0.0

class ForecastRepositoryImpl(
    private val currentWeatherDao: CurrentWeatherDao, //1
    private val forecastCityDao: ForecastCityDao, //2
    private val futureWeatherDao: FutureWeatherDao,
    private val weatherNetworkDataSource: WeatherNetworkDataSource, //3
    private val locationProvider: LocationProvider,  //4
    private val unitProvider: UnitProvider
) :ForecastRepository{
    init {
        weatherNetworkDataSource.apply {
            downloadedCurrentWeather.observeForever { newCurrentWeather ->
                persistFetchedCurrentWeather(newCurrentWeather)
            }
            downloadedFutureWeather.observeForever { newFutureWeather ->
                persistFetchedFutureWeather(newFutureWeather)
            }
        }
    }



    override suspend fun getCurrentWeather(metric: Boolean): LiveData<CurrentWeatherEntry> {
        return withContext(Dispatchers.IO) {
            initWeatherData()
            return@withContext currentWeatherDao.getWeather()

        }
    }

    override suspend fun getFutureWeather(latitude:String, longitude:String): List<ForecastCityModel> {
        return withContext(Dispatchers.IO){
            //   fetchFutureWeather(latitude,longitude,"current,hourly","metric")
            initWeatherData()
            Log.d("FetchedWeatherResponse","ForecastRepostoryImpl-1"+forecastCityDao.getForecastCity().toString())
            return@withContext forecastCityDao.getForecastCity()
        }

    }

    private fun persistFetchedCurrentWeather(fetchedWeather: CurrentWeatherResponse?) {
        GlobalScope.launch(Dispatchers.IO) {
            if (fetchedWeather != null) {
                currentWeatherDao.deleteAllCurrentWeather()
                currentWeatherDao.upsert(fetchedWeather.main)
            }
        }

    }
    fun deleteOldForecastData() {
        //   forecastCityDao.deleteAllForecastCities()
        futureWeatherDao.deleteAllFutureWeatherEntrys()
    }

    private fun persistFetchedFutureWeather(fetchedWeather: FutureWeatherResponse?) {

        GlobalScope.launch(Dispatchers.IO) {
            deleteOldForecastData()
            val futureWeatherList = fetchedWeather?.futureWeatherEntry

//            futureWeatherDao.insert(futureWeatherList)

            //   weatherLocationDao.upsert(fetchedWeather.location)


            // Local Room

            suspend fun insertForecastCity(forecastCity: ForecastCityModel) = try {
                ResultData.Success(data = forecastCityDao.insertForecastCity(forecastCity))
            }catch (e : Exception){
                //       ResultData.Failure(msg = e.message.toString())
            }

        }
    }

    private suspend fun initWeatherData(){
        fetchCurrentWeather()
        var fetchedCurrentWeather = weatherNetworkDataSource.fetchCurrentWeather(
            locationProvider.getPreferredLocationString(),
            unitProvider.getUnitSystem().toString() )
        Log.d("CurrentWeatherResponse","initweatherData fetchedCurrentWeather "+fetchedCurrentWeather.toString())

        fetchFutureWeather( "92.7917","56.0097","current,hourly", "metric")
        return
    }



    private suspend fun fetchCurrentWeather(){

        Log.d("CurrentWeatherResponse","fetchCurrentWeather location "+locationProvider.getPreferredLocationString())
        Log.d("CurrentWeatherResponse","fetchCurrentWeather units "+unitProvider.getUnitSystem().toString())

        weatherNetworkDataSource.fetchCurrentWeather(
            locationProvider.getPreferredLocationString(),
            unitProvider.getUnitSystem().toString()
        )



    }

    private suspend fun fetchFutureWeather(
        lon:String,
        lat:String,
        exclude:String="current,hourly",
        units:String="metric"
    ){
        weatherNetworkDataSource.fetchFutureWeather(lon, lat,exclude,units)
        Log.d("FetchedWeatherResponse","ForecastRepostoryImpl "+(weatherNetworkDataSource.fetchFutureWeather(lon, lat,exclude,units)).toString())

    }

    private fun isFetchCurrentNeeded(lastFetchTime: ZonedDateTime): Boolean {
        val thirtyMinutesAgo =ZonedDateTime.now().minusMinutes(30)
        return lastFetchTime.isBefore(thirtyMinutesAgo)
    }

}

