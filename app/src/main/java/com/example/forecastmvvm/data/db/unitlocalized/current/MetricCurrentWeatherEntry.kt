package com.resocoder.forecastmvvm.data.db.unitlocalized.current

import androidx.room.ColumnInfo
import com.example.forecastmvvm.data.db.unitlocalized.current.UnitSpecificCurrentWeatherEntry
import com.google.gson.annotations.SerializedName


data class MetricCurrentWeatherEntry(
/*
    @ColumnInfo(name = "tempC")
    override val temperature: Double,
    @ColumnInfo(name = "condition_text")
    override val conditionText: String,
    @ColumnInfo(name = "condition_icon")
    override val conditionIconUrl: String,
    @ColumnInfo(name = "windKph")
    override val windSpeed: Double,
    @ColumnInfo(name = "windDir")
    override val windDirection: String,
    @ColumnInfo(name = "precipMm")
    override val precipitationVolume: Double,
    @ColumnInfo(name = "feelslikeC")
    override val feelsLikeTemperature: Double,
    @ColumnInfo(name = "visKm")
    override val visibilityDistance: Double
*/
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

/*
e: D:\AndroidStudioProjects\ForecastMVVM\app\build\tmp\kapt3\stubs\debug\com\example\forecastmvvm\data\db
\CurrentWeatherDao.java:15: error:
The columns returned by the query does not have the fields
[temperature,conditionText,conditionIconUrl,windSpeed,precipitationVolume,feelsLikeTemperature,visibilityDistance]
[id,isDay,observationTime,precip,pressure,temperature,uvIndex,visibility,weatherCode,weatherDescriptions,weatherIcons,windDegree,windDir,windSpeed]

in com.resocoder.forecastmvvm.data.db.unitlocalized.current.MetricCurrentWeatherEntry
even though they are annotated as non-null or primitive. Columns returned by the query:
[id,isDay,observationTime,precip,pressure,temperature,uvIndex,visibility,weatherCode,weatherDescriptions,weatherIcons,windDegree,windDir,windSpeed]
    public abstract androidx.lifecycle.LiveData<com.resocoder.forecastmvvm.data.db.unitlocalized.current.MetricCurrentWeatherEntry> getWeatherMetric();
                                                                                                                                    ^

 */