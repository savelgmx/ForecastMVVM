package com.example.forecastmvvm.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.forecastmvvm.data.db.entity.CityModel
import com.example.forecastmvvm.data.db.entity.ForecastCityModel


@Database(

//    entities = [CurrentWeatherEntry::class, WeatherLocation::class],version = 1
        entities = [CityModel::class , ForecastCityModel::class], version = 2

)
@TypeConverters(Converters::class)

abstract class ForecastDatabase:RoomDatabase() {
/*
    abstract fun currentWeatherDao(): CurrentWeatherDao
    abstract fun weatherlocationDao():WeatherLocationDao
*/



    companion object {
        @Volatile private var instance: ForecastDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: buildDatabase(context).also { instance = it }
        }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(context.applicationContext,
                ForecastDatabase::class.java, "futureWeatherEntries.db")
                .build()
    }

}