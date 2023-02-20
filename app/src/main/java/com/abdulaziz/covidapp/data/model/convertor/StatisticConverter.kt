package com.abdulaziz.covidapp.data.model.convertor

import androidx.room.TypeConverter
import com.abdulaziz.covidapp.data.model.statistic.Statistic
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

class StatisticConverter {
    @TypeConverter
    fun fromStringList(list: List<Statistic?>?): String? {
        if (list == null) {
            return null
        }
        val gson = Gson()
        val type: Type = object : TypeToken<List<Statistic?>?>() {}.type
        return gson.toJson(list, type)
    }

    @TypeConverter
    fun toStringList(statisticString: String?): List<Statistic>? {
        if (statisticString == null) {
            return null
        }
        val gson = Gson()
        val type: Type = object : TypeToken<List<Statistic?>?>() {}.type
        return gson.fromJson<List<Statistic>>(statisticString, type)
    }
}