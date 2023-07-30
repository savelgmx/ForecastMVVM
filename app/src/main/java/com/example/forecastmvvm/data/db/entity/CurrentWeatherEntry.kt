package com.example.forecastmvvm.data.db.entity

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.forecastmvvm.data.network.response.Main
import com.example.forecastmvvm.data.network.response.Wind
import com.google.gson.annotations.SerializedName

const val CURRENT_WEATHER_ID = 0

@Entity(tableName = "current_weather")
data class CurrentWeatherEntry(


@SerializedName("feels_like")
val feelsLike: Double,
@SerializedName("grnd_level")
val grndLevel: Int,
val humidity: Int,
val pressure: Int,
@SerializedName("sea_level")
val seaLevel: Int,
val temp: Double,
@SerializedName("temp_max")
val tempMax: Double,
@SerializedName("temp_min")
val tempMin: Double,


    @Embedded(prefix="main_")
    val main:Main,

    @Embedded(prefix="wind_")
    val wind:Wind


    ){
    // PrimaryKey annotation to set idItem is unique [if you want that id autoGenerate set @field:PrimaryKey(autoGenerate = true)]
    @field:PrimaryKey(autoGenerate = true)
    var id : Long  = 0
}
