package com.example.forecastmvvm.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.forecastmvvm.data.db.entity.CityModel



@Dao
interface CityDao {

    // insert new city
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertCity(jobs : CityModel) : Long

    // Get all cities
    @Query("select * from city_table ")
    fun getAllCities(): List<CityModel>

    // Get size cities
    @Query("select COUNT(*) FROM city_table")
     fun getSizeCities(): Long

    // Delete all cities
    @Query("delete from city_table")
    fun deleteAllCities()

    // Delete city by id
    @Query("delete from city_table where id =:id")
    fun deleteCity(id : Long)

    // Delete forecastCity by id
    @Query("delete from forecast_city_table where id =:id")
    fun deleteForecastCity(id : Long)



}