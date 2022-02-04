package com.example.forecastmvvm.data.network

import com.example.forecastmvvm.data.network.response.CurrentWeatherResponse
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.coroutines.Deferred
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


const val API_KEY = "de5507f9445655f86f6cbcff4b04c2f7"

////http://api.apixu.com/v1/current.json?key=89e8bd89085b41b7a4b142029180210&q=London&lang=en

//http://api.weatherstack.com/current?access_key=de5507f9445655f86f6cbcff4b04c2f7&query=Krasnoyarsk&lang=en&unit=f

interface WeatherstackApiService {
    @GET("current")
    fun getCurrentWeather(
        @Query("query") location: String,
        @Query("lang") languageCode:String="en"
    ):Deferred<CurrentWeatherResponse>


    //http://api.weatherstack.com/forecast?access_key=de5507f9445655f86f6cbcff4b04c2f7&query=Krasnoyarsk&days=3

/*
    fun getFuturetWeather(
        @Query("query") location: String,
        @Query("days") days: Int,
    ): Deferred<FutureWeatherResponse>
*/



    //we need to create object which will actually fetch data from API and handle with interface
    companion object{
        operator fun invoke():WeatherstackApiService{
            val requestInterceptor = Interceptor{ chain ->
                val url = chain.request()
                    .url()
                    .newBuilder()
                    .addQueryParameter("access_key", API_KEY)
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
                .baseUrl("http://api.weatherstack.com/")
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(WeatherstackApiService::class.java)

        }
    }



}