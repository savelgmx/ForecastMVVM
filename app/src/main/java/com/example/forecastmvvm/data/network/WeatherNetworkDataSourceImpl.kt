package com.example.forecastmvvm.data.network

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.forecastmvvm.data.ResultData
import com.example.forecastmvvm.data.network.response.OpenWeatherResponse
import com.example.forecastmvvm.data.network.response.forecast.FutureWeatherResponse
import com.example.forecastmvvm.domain.api.OpenWeatherApiService
import com.example.forecastmvvm.internal.NoConnectivityException
import com.example.forecastmvvm.internal.NoInternetException
import com.google.android.gms.common.api.ApiException
import retrofit2.Response

class WeatherNetworkDataSourceImpl(
    private val openWeatherApiService: OpenWeatherApiService
) : WeatherNetworkDataSource {
    private val _downloadedCurrentWeather = MutableLiveData<OpenWeatherResponse>()
    override val downloadedCurrentWeather: LiveData<OpenWeatherResponse>
        get() = _downloadedCurrentWeather

    override suspend fun fetchCurrentWeather(q: String, units: String) {
        try {
            val fetchedCurrentWeather = openWeatherApiService
                .getCurrentWeather(q,units)
                .await()
            _downloadedCurrentWeather.postValue(fetchedCurrentWeather)
        }
        catch (e: NoConnectivityException) {
            Log.e("Connectivity", "No internet connection.", e)
        }
    }

    private val _downloadedFutureWeather= MutableLiveData<FutureWeatherResponse>()
    override val downloadedFutureWeather: LiveData<FutureWeatherResponse>
    get() = _downloadedFutureWeather


    override suspend fun fetchFutureWeather(
        lon:String,
        lat:String,
        exclude:String,
        units: String
    ) {

        try{
            val fetchedFutureWeather = openWeatherApiService
                .getForecastweather(lon,lat,exclude,units)
                .await()

            Log.d("FetchedWeatherResponse","WeatherNetworkDataSourceImpl "+fetchedFutureWeather.toString())
            _downloadedFutureWeather.postValue(fetchedFutureWeather)


        }
        catch (e:NoConnectivityException){
            Log.e("Connectivity", "No internet connection.", e)
        }

    }

    override suspend fun getWeatherOfLatLon(latitude:String, longitude:String) = getResult {
        openWeatherApiService.getWeatherOfLatLon(latitude, longitude)
    }


        protected suspend fun <T> getResult(call: suspend () -> Response<T>): ResultData<T> {
            try {
                val response = call()
                if (response.isSuccessful) {
                    val body = response.body()
                    if (body != null) return ResultData.Success(body)
                }
                return ResultData.Failure(
                    msg = response.code().toString() + " " + response.message().toString()
                )
            } catch (e: ApiException) {
                return ResultData.Failure(msg = e.message.toString())
            } catch (e: NoInternetException) {
                return ResultData.Internet()
            } catch (e: Exception) {
                return ResultData.Failure(msg = e.message.toString())
            }
        }


}