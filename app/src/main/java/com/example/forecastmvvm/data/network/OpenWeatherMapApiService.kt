package com.example.forecastmvvm.data.network

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


const val APIKEY="33cc710b4ef18155198d89c3b2033f56"

//we will use openweather API
//https://api.openweathermap.org/data/2.5/weather?q=Krasnoyarsk&appid=33cc710b4ef18155198d89c3b2033f56&units=metric

interface OpenWeatherMapApiService {

    @GET("main")
    fun getCurrentWeather(
        @Query("q") location:String
    )
    fun getFutureWeather()



    //we need to create object which will actually fetch data from API and handle with interface
    companion object{
        operator fun invoke():OpenWeatherMapApiService{
            val requestInterceptor = Interceptor{ chain ->
                val url = chain.request()
                    .url()
                    .newBuilder()
                    .addQueryParameter("access_key", APIKEY)
                    .build()
                val request=chain.request()
                    .newBuilder()
                    .url(url)
                    .build()

                return@Interceptor chain.proceed(request)
            }
            val okHttpClient= OkHttpClient.Builder()
                .addInterceptor(requestInterceptor)
                //          .addInterceptor(connectivityInterceptor)

                .build()

            return Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl("api.openweathermap.org")
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(OpenWeatherMapApiService::class.java)

        }
    }



}