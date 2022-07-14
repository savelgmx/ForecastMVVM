package com.example.forecastmvvm.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import com.example.forecastmvvm.data.ResultData
import com.example.forecastmvvm.data.db.CityDao
import com.example.forecastmvvm.data.db.ForecastCityDao
import com.example.forecastmvvm.data.db.entity.CityModel
import com.example.forecastmvvm.data.db.entity.ForecastCityModel
import com.example.forecastmvvm.data.network.WeatherNetworkDataSource
import com.example.forecastmvvm.data.network.response.OpenWeatherResponse
import com.example.forecastmvvm.data.network.response.forecast.FutureWeatherResponse
import com.example.forecastmvvm.data.provider.LocationProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.threeten.bp.LocalDate
import org.threeten.bp.ZonedDateTime

import java.util.*

class ForecastRepositoryImpl(
    private val cityDao: CityDao, //1
    private val forecastCityDao: ForecastCityDao, //2
    private val weatherNetworkDataSource: WeatherNetworkDataSource, //3
    private val locationProvider: LocationProvider  //4
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



    override suspend fun getCurrentWeather(metric: Boolean): List<CityModel> {
        return withContext(Dispatchers.IO) {
            initWeatherData()
            return@withContext if (metric) cityDao.getAllCities()
            else cityDao.getAllCities()

        }
    }

    override suspend fun getFutureWeather(latitude:String, longitude:String): LiveData<ForecastCityModel> {
        return withContext(Dispatchers.IO){
             //   fetchFutureWeather(latitude,longitude,"current,hourly","metric")
            initWeatherData()
            Log.d("FetchedWeatherResponse","ForecastRepostoryImpl-1"+forecastCityDao.getForecastCity().toString())
            return@withContext forecastCityDao.getForecastCity()
        }

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

    private fun persistFetchedFutureWeather(fetchedWeather: FutureWeatherResponse) {

        fun deleteOldForecastData() {
            val today = LocalDate.now()
            forecastCityDao.deleteAllForecastCities()
        }

        GlobalScope.launch(Dispatchers.IO) {
            deleteOldForecastData()
            val futureWeatherList = fetchedWeather.copy()
         //   forecastCityDao.insertForecastCity(futureWeatherList)
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
 //       val lastWeatherLocation = weatherLocationDao.getLocation().value

   //     if (lastWeatherLocation == null
     //       || locationProvider.hasLocationChanged(lastWeatherLocation)) {
            fetchCurrentWeather()
            fetchFutureWeather( "92.7917","56.0097","current,hourly", "metric")
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

    override fun getWeatherOfLatLon(): Flow<ResultData<FutureWeatherResponse>> = flow {
        emit(weatherNetworkDataSource.getWeatherOfLatLon("56.0097",
            "92.7917"))
        //TODO remove hardcoded param long latitude
    }


}

