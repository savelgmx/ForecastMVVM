package com.example.forecastmvvm.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.forecastmvvm.data.db.entity.ForecastCityModel

@Dao
interface ForecastCityDao {

    // insert new ForecastCity suspend
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertForecastCity(forecastCity : ForecastCityModel) : Long

    // Get forecastCity suspend
    @Query("select * from forecast_city_table where id =:id")
     fun getForecastCity(id : Long): ForecastCityModel

    // Get size forecastCities suspend
    @Query("select COUNT(*) FROM forecast_city_table")
    fun getSizeForecastCities(): Long

    // Delete all forecastCities suspend
    @Query("delete from forecast_city_table")
    fun deleteAllForecastCities()

    // Delete forecastCity by id suspend
    @Query("delete from forecast_city_table where id =:id")
     fun deleteForecastCity(id : Long)



}