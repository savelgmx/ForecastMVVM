package com.example.forecastmvvm.data.network.response


import com.google.gson.annotations.SerializedName

        data class OpenWeatherResponse(

                @field:SerializedName("weather")
        val weather: List<Weather?>? = null,

                @field:SerializedName("name")
        val name: String? = null,

                @field:SerializedName("cod")
        val cod: Int? = null,

                @field:SerializedName("main")
        val main: Main? = null,

                @field:SerializedName("clouds")
        val clouds: Clouds? = null,

                @field:SerializedName("id")
        val id: Int? = null,

                @field:SerializedName("sys")
        val sys: Sys? = null,

                @field:SerializedName("base")
        val base: String? = null,

                @field:SerializedName("wind")
        val wind: Wind? = null
)