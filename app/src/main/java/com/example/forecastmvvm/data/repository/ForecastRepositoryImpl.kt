package com.example.forecastmvvm.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import com.example.forecastmvvm.data.ResultData
import com.example.forecastmvvm.data.db.CurrentWeatherDao
import com.example.forecastmvvm.data.db.ForecastCityDao
import com.example.forecastmvvm.data.db.FutureWeatherDao
import com.example.forecastmvvm.data.db.entity.CurrentWeatherEntry
import com.example.forecastmvvm.data.db.entity.ForecastCityModel
//import com.example.forecastmvvm.data.network.WeatherNetworkDataSource
import com.example.forecastmvvm.data.network.api.OpenWeatherApiService
import com.example.forecastmvvm.data.network.response.current.CurrentWeatherResponse
import com.example.forecastmvvm.data.network.response.forecast.FutureWeatherResponse
import com.example.forecastmvvm.data.provider.LocationProvider
import com.example.forecastmvvm.internal.NoConnectivityException
import com.resocoder.forecastmvvm.data.provider.UnitProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.threeten.bp.ZonedDateTime
import java.util.*

private var longitude:Double = 0.0
private var latitude:Double = 0.0

class ForecastRepositoryImpl(
    private val currentWeatherDao: CurrentWeatherDao, //1
    private val forecastCityDao: ForecastCityDao, //2
    private val futureWeatherDao: FutureWeatherDao,//3
    private val locationProvider: LocationProvider,//4
    private val unitProvider: UnitProvider,//5
    private val openWeatherApiService: OpenWeatherApiService // Injected OpenWeatherApiService 6
) :ForecastRepository{

    override suspend fun getCurrentWeather(metric: Boolean): CurrentWeatherEntry {
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

    override suspend fun refreshCurrentWeather() {
        fetchCurrentWeather()
    }

    fun deleteOldForecastData() {
        forecastCityDao.deleteAllForecastCities()
        futureWeatherDao.deleteAllFutureWeatherEntrys()
    }

  private suspend fun initWeatherData(){
        fetchCurrentWeather()
     //   fetchFutureWeather(locationProvider.getPreferredLocationString(), "metric", Locale.getDefault().language)
     //TODO change parameters
    }
   private suspend fun fetchCurrentWeather(){
        val fetchedCurrentWeather = fetchCurrentWeatherFromApi(
            locationProvider.getPreferredLocationString(),
            unitProvider.getUnitSystem().toString(),
            Locale.getDefault().language
        )

        if (fetchedCurrentWeather != null) {
            currentWeatherDao.deleteAllCurrentWeather()
            currentWeatherDao.upsert(fetchedCurrentWeather.main)
        }
    }

    private suspend fun fetchCurrentWeatherFromApi(
        q: String,
        units: String,
        lang: String
    ): CurrentWeatherResponse? {
        return try {
            openWeatherApiService.getCurrentWeather(q, units, lang).await()
        } catch (e: NoConnectivityException) {
            handleConnectivityException()
            null
        }
    }

    private suspend fun handleConnectivityException() {
        // Handle the connectivity issue gracefully
        Log.e("Connectivity", "No internet connection.")
        // Attempt to retrieve data from local cache
        retrieveDataFromLocalCache()
    }

    private fun retrieveDataFromLocalCache() {
        // TODO Implement logic to retrieve data from the local cache
        // You can return default or cached data
    }

    private suspend fun fetchFutureWeather(lon: String, lat: String, exclude: String, units: String) {
        val fetchedFutureWeather = fetchFutureWeatherFromApi(lon, lat, exclude, units)

        if (fetchedFutureWeather != null) {
            deleteOldForecastData()
            val futureWeatherList = fetchedFutureWeather.futureWeatherEntry

            if (futureWeatherList != null) {
                //   forecastCityDao.insertAllForecastCities(futureWeatherList)
                futureWeatherDao.deleteAllFutureWeatherEntrys()
                futureWeatherDao.insert(futureWeatherList)
            }
        }
    }

    private suspend fun fetchFutureWeatherFromApi(lon: String, lat: String, exclude: String, units: String): FutureWeatherResponse? {
        return try {
            openWeatherApiService.getForecastweather(lon, lat, exclude, units, Locale.getDefault().language).await()
        } catch (e: NoConnectivityException) {
            handleConnectivityException()
            null
        }
    }
}
