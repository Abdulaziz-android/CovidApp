package com.abdulaziz.covidapp.repositories

import com.abdulaziz.covidapp.data.local.dao.NewsDao
import com.abdulaziz.covidapp.data.local.dao.StatisticsByCountryDao
import com.abdulaziz.covidapp.data.local.entitiy.CountryStatisticEntity
import com.abdulaziz.covidapp.data.local.entitiy.NewsEntity
import com.abdulaziz.covidapp.data.remote.NewsApiService
import com.abdulaziz.covidapp.data.remote.StatisticsApiService
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class StatisticCountryRepository @Inject constructor(
    private val newsApiService: NewsApiService,
    private val newsDao: NewsDao,
    private val statisticsApiService: StatisticsApiService,
    private val statisticByCountryDao: StatisticsByCountryDao
) {

    // News Repositories
    suspend fun getNews() = flow { emit(newsApiService.getNews(limit = 3)) }
    suspend fun getAllNews() = newsDao.getAllNews()
    suspend fun saveNewsList(list: List<NewsEntity>) = newsDao.insertListNews(list)

    // Statistic Repositories
    suspend fun saveStatistics(statistic: CountryStatisticEntity) = statisticByCountryDao.insert(statistic)
    suspend fun getLocalStatistics(country: String) =
        statisticByCountryDao.getStatisticsByCountryCode(country)

    suspend fun getRemoteStatistics(country: String, from: String, to: String) = flow {
        emit(statisticsApiService.getData(country = country, from = from, to = to))
    }


}