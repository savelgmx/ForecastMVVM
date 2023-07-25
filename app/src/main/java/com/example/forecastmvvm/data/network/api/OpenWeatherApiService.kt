package com.example.forecastmvvm.data.network.api

import com.alialfayed.weathertask.domain.model.WeatherCityResponse
import com.example.forecastmvvm.data.network.response.current.CurrentWeatherResponse
import com.example.forecastmvvm.data.network.response.forecast.FutureWeatherResponse
import com.example.forecastmvvm.internal.AppConstants
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.coroutines.Deferred
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.concurrent.TimeUnit


//const val APIKEY="33cc710b4ef18155198d89c3b2033f56"

//we will use openweather API
//https://api.openweathermap.org/data/2.5/weather?q=Krasnoyarsk&appid=33cc710b4ef18155198d89c3b2033f56&units=metric
//https://api.openweathermap.org/data/2.5/weather?appid=33cc710b4ef18155198d89c3b2033f56&units=metric&q=Krasnoyarsk

interface OpenWeatherApiService {


    @GET("weather")
    fun getCurrentWeather(@Query("q") q: String,
                          @Query("units") units: String): Deferred<CurrentWeatherResponse>


//for Forecast Weather we must use
//https://api.openweathermap.org/data/2.5/onecall?appid=33cc710b4ef18155198d89c3b2033f56&lon=92.791&lat=56.0097&exclude=current,hourly&units=metric

    @GET("onecall")
    fun getForecastweather(@Query("lon") lon:String,
                           @Query("lat")  lat:String,
                           @Query("exclude") exclude:String="current,hourly",
                           @Query("units") units: String
    ):Deferred<FutureWeatherResponse>



    @GET("onecall")
    suspend fun getWeatherOfLatLon(
        @Query("lon") lon:String,
        @Query("lat")  lat:String,
        @Query("exclude") exclude:String="current,hourly",
        @Query("units") units: String=AppConstants.WEATHER_UNIT
    ): Response<WeatherCityResponse>


    //we need to create object which will actually fetch data from API and handle with interface
//
    companion   object{
        operator fun invoke(
            connectivityInterceptor: ConnectivityInterceptor
        ): OpenWeatherApiService {
            val requestInterceptor = Interceptor{ chain ->
                val url = chain.request()
                    .url()
                    .newBuilder()
                    .addQueryParameter("appid", AppConstants.WEATHER_API_KEY)
                    .build()
                val request=chain.request()
                    .newBuilder()
                    .url(url)
                    .build()

                return@Interceptor chain.proceed(request)
            }


            val okHttpClient= OkHttpClient.Builder()
                .connectTimeout(40, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)

                .addInterceptor(requestInterceptor)
                .addInterceptor(connectivityInterceptor)

                .build()

            return Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(AppConstants.BASE_URL_RETROFIT_API) //"https://api.openweathermap.org"
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(OpenWeatherApiService::class.java)

        }
    }



}