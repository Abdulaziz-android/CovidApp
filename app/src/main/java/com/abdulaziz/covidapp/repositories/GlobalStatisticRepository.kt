package com.abdulaziz.covidapp.repositories

import com.abdulaziz.covidapp.data.local.dao.NewsDao
import com.abdulaziz.covidapp.data.local.dao.StatisticGlobalDao
import com.abdulaziz.covidapp.data.local.entitiy.GlobalScoreEntity
import com.abdulaziz.covidapp.data.local.entitiy.GlobalStatisticListEntity
import com.abdulaziz.covidapp.data.local.entitiy.NewsEntity
import com.abdulaziz.covidapp.data.remote.NewsApiService
import com.abdulaziz.covidapp.data.remote.StatisticsApiService
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GlobalStatisticRepository @Inject constructor(
    private val newsApiService: NewsApiService,
    private val statisticsApiService: StatisticsApiService,
    private val statisticGlobalDao: StatisticGlobalDao,
    private val newsDao: NewsDao,
) {

    // News Repositories
    suspend fun getNews() = flow { emit(newsApiService.getNews(limit = 3)) }
    suspend fun getAllNews() = newsDao.getAllNews()
    suspend fun saveNewsList(list: List<NewsEntity>) = newsDao.insertListNews(list)

    suspend fun getRemoteGlobalScore() = flow { emit(statisticsApiService.getGlobalScore()) }
    suspend fun saveGlobalScore(globalScoreEntity: GlobalScoreEntity) = statisticGlobalDao.insertGlobal(globalScoreEntity)
    suspend fun getLocalGlobalScore() = statisticGlobalDao.getGlobalEntity()

    suspend fun saveGlobalStatistics(statistic:GlobalStatisticListEntity) = statisticGlobalDao.insertGlobalList(statistic)
    suspend fun getLocalGlobalStatistics() = statisticGlobalDao.getGlobalListEntity()
    suspend fun getRemoteGlobalStatistics(from: String, to: String) = flow {
        emit(statisticsApiService.getGlobalDailyData(from = from, to = to))
    }

}