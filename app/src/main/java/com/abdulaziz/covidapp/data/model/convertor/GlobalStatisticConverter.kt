package com.abdulaziz.covidapp.data.model.convertor

import androidx.room.TypeConverter
import com.abdulaziz.covidapp.data.model.global.daily.GlobalStatistic
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

class GlobalStatisticConverter {
    @TypeConverter
    fun fromStringList(list: List<GlobalStatistic?>?): String? {
        if (list == null) {
            return null
        }
        val gson = Gson()
        val type: Type = object : TypeToken<List<GlobalStatistic?>?>() {}.type
        return gson.toJson(list, type)
    }

    @TypeConverter
    fun toStringList(statisticString: String?): List<GlobalStatistic>? {
        if (statisticString == null) {
            return null
        }
        val gson = Gson()
        val type: Type = object : TypeToken<List<GlobalStatistic?>?>() {}.type
        return gson.fromJson<List<GlobalStatistic>>(statisticString, type)
    }
}