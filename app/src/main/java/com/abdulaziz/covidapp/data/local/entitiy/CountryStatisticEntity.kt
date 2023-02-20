package com.abdulaziz.covidapp.data.local.entitiy

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.abdulaziz.covidapp.data.model.convertor.StatisticConverter
import com.abdulaziz.covidapp.data.model.statistic.Statistic

@Entity(tableName = "statistic_table")
data class CountryStatisticEntity(
    @PrimaryKey
    val country:String,
    val country_code:String?=null,
    val date:String?=null,
    @TypeConverters(StatisticConverter::class)
    val list : List<Statistic>,
)