package com.example.forecastmvvm.data.network

import com.example.forecastmvvm.data.network.response.OpenWeatherResponse
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.coroutines.Deferred
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


const val APIKEY="33cc710b4ef18155198d89c3b2033f56"

//we will use openweather API
//https://api.openweathermap.org/data/2.5/weather?q=Krasnoyarsk&appid=33cc710b4ef18155198d89c3b2033f56&units=metric
//https://api.openweathermap.org/data/2.5/weather?appid=33cc710b4ef18155198d89c3b2033f56&units=metric&q=Krasnoyarsk

interface OpenWeatherApiService {


    @GET("/data/2.5/weather")
    fun getCurrentWeather(@Query("q") q: String,
                   @Query("units") units: String): Deferred<OpenWeatherResponse>



    //we need to create object which will actually fetch data from API and handle with interface
//
    companion   object{
        operator fun invoke():OpenWeatherApiService{
            val requestInterceptor = Interceptor{ chain ->
                val url = chain.request()
                    .url()
                    .newBuilder()
                    .addQueryParameter("appid", APIKEY)
                    .build()
                val request=chain.request()
                    .newBuilder()
                    .url(url)
                    .build()

                return@Interceptor chain.proceed(request)
            }
            val okHttpClient= OkHttpClient.Builder()
                .addInterceptor(requestInterceptor)
          //      .addInterceptor(connectivityInterceptor)

                .build()

            return Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl("https://api.openweathermap.org")
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(OpenWeatherApiService::class.java)

        }
    }



}