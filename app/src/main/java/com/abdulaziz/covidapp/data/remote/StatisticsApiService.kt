package com.abdulaziz.covidapp.data.remote

import com.abdulaziz.covidapp.data.model.global.GlobalData
import com.abdulaziz.covidapp.data.model.global.daily.GlobalStatistic
import com.abdulaziz.covidapp.data.model.statistic.Statistic
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface StatisticsApiService {
    @GET("country/{country_name}")
    suspend fun getData(
        @Path("country_name") country:String,
        @Query("from") from:String,
        @Query("to") to:String
    ):Response<List<Statistic>>

    @GET("summary")
    suspend fun getGlobalScore(
    ):Response<GlobalData>

    @GET("world")
    suspend fun getGlobalDailyData(
        @Query("from") from:String,
        @Query("to") to:String
    ):Response<List<GlobalStatistic>>

}