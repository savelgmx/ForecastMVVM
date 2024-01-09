package com.example.forecastmvvm.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

const val CURRENT_WEATHER_ID = 0

@Entity(tableName = "current_main")
data class CurrentMainEntry(


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
val tempMin: Double
    ){
    // PrimaryKey annotation to set idItem is unique [if you want that id autoGenerate set @field:PrimaryKey(autoGenerate = true)]
    @field:PrimaryKey(autoGenerate = true)
    var id : Long  = 0
}
