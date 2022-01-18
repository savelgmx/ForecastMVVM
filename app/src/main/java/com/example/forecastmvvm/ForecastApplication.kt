package com.example.forecastmvvm;

import android.app.Application;
import com.example.forecastmvvm.data.db.ForecastDatabase
import com.example.forecastmvvm.data.network.*
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton

public class ForecastApplication : Application(),KodeinAware {

    override val kodein = Kodein.lazy {
        import(androidXModule(this@ForecastApplication)) //provides us with Context,various services and anything related to Android
        bind() from singleton { ForecastDatabase(instance()) } //instance() gives us Context
        bind() from singleton { instance<ForecastDatabase>().currentWeatherDao() }
        bind<ConnectivityInterceptor>() with singleton { ConnectivityInterceptorImpl(instance()) }
        bind() from singleton { WeatherstackApiService(instance()) }
        bind<WeatherNetworkDataSource>() with singleton { WeatherNetworkDataSourceImpl(instance()) }



    }
}
