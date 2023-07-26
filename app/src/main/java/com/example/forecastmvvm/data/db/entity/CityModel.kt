package com.example.forecastmvvm.data.db.entity

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.forecastmvvm.data.network.response.Main
import com.example.forecastmvvm.data.network.response.Wind
import com.example.forecastmvvm.data.network.response.forecast.Weather

@Entity(tableName = "city_table")
data class CityModel(

    @ColumnInfo(name = "name")
    val name: String? = null,

    @ColumnInfo(name = "temp")
    val temp: Double? = null ,

    @ColumnInfo(name = "icon")
    val icon: String? = null,


    @Embedded(prefix="main_")
    val main:Main,

    @Embedded(prefix="wind_")
    val wind:Wind


    ){
    // PrimaryKey annotation to set idItem is unique [if you want that id autoGenerate set @field:PrimaryKey(autoGenerate = true)]
    @field:PrimaryKey(autoGenerate = true)
    var id : Long  = 0
}
