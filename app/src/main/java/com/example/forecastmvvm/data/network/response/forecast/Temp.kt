package com.example.forecastmvvm.data.network.response.forecast


import com.google.gson.annotations.SerializedName

data class Temp(
    val day: Double,
    val eve: Double,
    val max: Double,
    val min: Double,
    val morn: Double,
    val night: Double
)