package com.example.forecastmvvm.data.db

import androidx.room.TypeConverter
import com.example.forecastmvvm.data.db.entity.ForecastRow
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

}

