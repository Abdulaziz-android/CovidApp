package com.abdulaziz.covidapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.abdulaziz.covidapp.data.local.dao.StatisticGlobalDao
import com.abdulaziz.covidapp.data.local.entitiy.NewsEntity
import com.abdulaziz.covidapp.data.local.dao.NewsDao
import com.abdulaziz.covidapp.data.local.dao.StatisticsByCountryDao
import com.abdulaziz.covidapp.data.model.convertor.StatisticConverter
import com.abdulaziz.covidapp.data.local.entitiy.CountryStatisticEntity
import com.abdulaziz.covidapp.data.local.entitiy.GlobalScoreEntity
import com.abdulaziz.covidapp.data.local.entitiy.GlobalStatisticListEntity
import com.abdulaziz.covidapp.data.model.convertor.GlobalStatisticConverter

@Database(entities = [NewsEntity::class, CountryStatisticEntity::class, GlobalScoreEntity::class, GlobalStatisticListEntity::class], version = 1)
@TypeConverters(StatisticConverter::class, GlobalStatisticConverter::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun newsDao() : NewsDao
    abstract fun statisticCountryDao():StatisticsByCountryDao
    abstract fun statisticGlobalDao():StatisticGlobalDao
}