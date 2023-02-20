package com.abdulaziz.covidapp.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.abdulaziz.covidapp.data.local.entitiy.GlobalScoreEntity
import com.abdulaziz.covidapp.data.local.entitiy.GlobalStatisticListEntity

@Dao
interface StatisticGlobalDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGlobal(globalScoreEntity: GlobalScoreEntity)

    @Query("select * from global_score_table")
    suspend fun getGlobalEntity():GlobalScoreEntity?


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGlobalList(globalStatisticListEntity: GlobalStatisticListEntity)

    @Query("select * from globalstatisticlistentity")
    suspend fun getGlobalListEntity():GlobalStatisticListEntity?

}