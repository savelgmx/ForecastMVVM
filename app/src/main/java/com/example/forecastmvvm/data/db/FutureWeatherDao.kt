package com.example.forecastmvvm.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.forecastmvvm.data.db.entity.FutureWeatherEntry

@Dao
interface FutureWeatherDao {

    @Insert(onConflict= OnConflictStrategy.REPLACE )
    fun insert(futureWeatherEntry: FutureWeatherEntry)

    @Query("select * from future_weather")
    fun getFutureWeather(): LiveData<FutureWeatherEntry>

    // Get size futureWeatherEntry suspend
    @Query("select COUNT(*) FROM future_weather")
    fun getSizeForecastCities(): Long

    // Delete all future_weather suspend
    @Query("delete from future_weather")
    fun deleteAllFutureWeatherEntrys()


}