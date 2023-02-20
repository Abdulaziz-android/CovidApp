package com.abdulaziz.covidapp.data.local.entitiy

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.abdulaziz.covidapp.data.model.convertor.GlobalStatisticConverter
import com.abdulaziz.covidapp.data.model.global.daily.GlobalStatistic

@Entity
data class GlobalStatisticListEntity(
    @PrimaryKey
    val id: Int =1,
    @TypeConverters(GlobalStatisticConverter::class)
    val list: List<GlobalStatistic>
)