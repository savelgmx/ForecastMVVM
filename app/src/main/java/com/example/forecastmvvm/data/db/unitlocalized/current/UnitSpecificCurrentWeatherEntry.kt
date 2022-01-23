package com.example.forecastmvvm.data.db.unitlocalized.current

import androidx.room.ColumnInfo

interface UnitSpecificCurrentWeatherEntry {
/*
    val temperature: Double
    val conditionText: String
    val conditionIconUrl: String
    val windSpeed: Double
    val windDirection: String
    val precipitationVolume: Double
    val feelsLikeTemperature: Double
    val visibilityDistance: Double
*/

    val is_day: String
    val observationTime: String
    val precip: Int
    val pressure: Int
    val temperature: Int
    val uvIndex: Int
    val visibility: Int
    val weatherCode: Int
/*
    val weatherDescriptions: List<String>
    val weatherIcons: List<String>
*/
    val windDegree: Int
    val windDir: String
    val windSpeed: Int

}