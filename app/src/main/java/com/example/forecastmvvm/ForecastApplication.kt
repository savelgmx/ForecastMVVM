package com.example.forecastmvvm;

import android.app.Application;
import android.content.Context
import android.preference.PreferenceManager
import com.example.forecastmvvm.data.db.ForecastDatabase
import com.example.forecastmvvm.data.network.*
import com.example.forecastmvvm.data.provider.LocationProvider
import com.example.forecastmvvm.data.provider.LocationProviderImpl
import com.example.forecastmvvm.data.repository.ForecastRepository
import com.example.forecastmvvm.data.repository.ForecastRepositoryImpl
import com.example.forecastmvvm.data.network.api.ConnectivityInterceptor
import com.example.forecastmvvm.data.network.api.ConnectivityInterceptorImpl
import com.example.forecastmvvm.data.network.api.OpenWeatherApiService
import com.example.forecastmvvm.data.network.response.forecast.Daily
import com.example.forecastmvvm.data.provider.DailyObjectProvider
import com.example.forecastmvvm.ui.weather.current.CurrentWeatherViewModelFactory
import com.example.forecastmvvm.ui.weather.future.detail.FutureDetailWeatherViewModelFactory
import com.example.forecastmvvm.ui.weather.future.list.FutureListWeatherViewModelFactory
import com.google.android.gms.location.LocationServices
import com.jakewharton.threetenabp.AndroidThreeTen
import com.resocoder.forecastmvvm.data.provider.UnitProvider
import com.resocoder.forecastmvvm.data.provider.UnitProviderImpl
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.*

class ForecastApplication : Application(), KodeinAware {
    override val kodein = Kodein.lazy {
        import(androidXModule(this@ForecastApplication))

        bind<Context>("ApplicationContext") with singleton { this@ForecastApplication.applicationContext }

        bind() from singleton { ForecastDatabase(instance()) }
        bind() from singleton { instance<ForecastDatabase>().currentWeatherDao() }
        bind() from singleton { instance<ForecastDatabase>().forecastCityDao() }
        bind() from singleton { instance<ForecastDatabase>().futureWeatherDao() }


        bind<ConnectivityInterceptor>() with singleton { ConnectivityInterceptorImpl(instance()) }
        bind() from singleton { OpenWeatherApiService(instance()) }
        bind<WeatherNetworkDataSource>() with singleton { WeatherNetworkDataSourceImpl(instance()) }
        bind() from provider { LocationServices.getFusedLocationProviderClient(instance<Context>()) }
        bind<LocationProvider>() with singleton { LocationProviderImpl(instance(), instance()) }
        bind<ForecastRepository>() with singleton { ForecastRepositoryImpl(instance(), instance(),instance(),instance(),instance(),instance()) }
        bind<UnitProvider>() with singleton { UnitProviderImpl(instance()) }
        bind() from provider { CurrentWeatherViewModelFactory(instance(),instance()) }
        bind() from factory { latitude:String,longitude:String-> FutureListWeatherViewModelFactory(instance(),latitude ,longitude, instance()) }
        bind() from factory { dailyObjectProvider: DailyObjectProvider -> FutureDetailWeatherViewModelFactory(dailyObjectProvider) }



    }

    override fun onCreate() {
        super.onCreate()
        AndroidThreeTen.init(this)
        PreferenceManager.setDefaultValues(this, R.xml.preferences, false)
    }
}