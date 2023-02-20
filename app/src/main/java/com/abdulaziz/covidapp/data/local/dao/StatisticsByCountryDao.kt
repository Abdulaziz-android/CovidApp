package com.abdulaziz.covidapp.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.abdulaziz.covidapp.data.local.entitiy.CountryStatisticEntity

@Dao
interface StatisticsByCountryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(statisticEntity: CountryStatisticEntity)

    @Query("select * from statistic_table where country = :country")
    suspend fun getStatisticsByCountryCode(country:String): CountryStatisticEntity?

}