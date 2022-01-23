package com.resocoder.forecastmvvm.data.db.unitlocalized.current

import androidx.room.ColumnInfo
import com.example.forecastmvvm.data.db.unitlocalized.current.UnitSpecificCurrentWeatherEntry


data class ImperialCurrentWeatherEntry(
    @ColumnInfo(name= "isDay")
    override val is_day: String,
    @ColumnInfo(name="observationTime")
    override val observationTime: String,
    @ColumnInfo(name="precip")
    override val precip: Int,
    @ColumnInfo(name="pressure")
    override val pressure: Int,
    @ColumnInfo(name="temperature")
    override val temperature: Int,
    @ColumnInfo(name="uvIndex")
    override val uvIndex: Int,
    @ColumnInfo(name="visibility")
    override val visibility: Int,
    @ColumnInfo(name="weatherCode")
    override val weatherCode: Int,
/*
    @ColumnInfo(name="weatherDescriptions")
    override val weatherDescriptions: List<String>,
    @ColumnInfo(name="weather_icons")
    override val weatherIcons: List<String>,
*/
    @ColumnInfo(name="windDegree")
    override val windDegree: Int,
    @ColumnInfo(name="windDir")
    override val windDir: String,
    @ColumnInfo(name="windSpeed")
    override val windSpeed: Int
) : UnitSpecificCurrentWeatherEntry