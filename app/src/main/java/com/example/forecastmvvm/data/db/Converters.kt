package com.example.forecastmvvm.data.db

import androidx.room.TypeConverter
import com.example.forecastmvvm.data.db.entity.ForecastRow
import com.example.forecastmvvm.data.network.response.forecast.Alert
import com.example.forecastmvvm.data.network.response.forecast.Daily
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

class Converters {
    @TypeConverter
    fun fromString(stringListString: String): List<String> {
        return stringListString.split(",").map { it }
    }

    @TypeConverter
    fun toString(stringList: List<String>): String {
        return stringList.joinToString(separator = ",")
    }

    @TypeConverter
    fun setIntModel(ints: List<ForecastRow>): String = Gson().toJson(ints)

    @TypeConverter
    fun getIntModel(jsonModel: String): List<ForecastRow> {
        val listType: Type = object : TypeToken<List<ForecastRow>>() {}.type
        return Gson().fromJson(jsonModel, listType)
    }

    @TypeConverter
    fun setAlertModel(ints: List<Alert>): String = Gson().toJson(ints)

    @TypeConverter
    fun getAlertModel(jsonModel: String): List<Alert> {
        val listType: Type = object : TypeToken<List<Alert>>() {}.type
        return Gson().fromJson(jsonModel, listType)
    }

    @TypeConverter
    fun setDailyModel(ints: List<Daily>): String = Gson().toJson(ints)

    @TypeConverter
    fun getDailyModel(jsonModel: String): List<Daily> {
        val listType: Type = object : TypeToken<List<Daily>>() {}.type
        return Gson().fromJson(jsonModel, listType)
    }

}

