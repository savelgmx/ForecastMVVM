package com.example.forecastmvvm.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.forecastmvvm.data.db.entity.CurrentMainEntry



@Dao
interface CurrentWeatherDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsert(mainEntry: CurrentMainEntry)

    @Query("select * from current_main")
    fun getMainWeather(): (CurrentMainEntry)

    @Query("delete from current_main")
    fun deleteAllMainWeather()



}