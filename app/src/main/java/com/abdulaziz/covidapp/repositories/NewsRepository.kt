package com.abdulaziz.covidapp.repositories

import com.abdulaziz.covidapp.data.local.dao.NewsDao
import com.abdulaziz.covidapp.data.local.entitiy.NewsEntity
import com.abdulaziz.covidapp.data.remote.NewsApiService
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class NewsRepository @Inject constructor(
    private val newsApiService: NewsApiService,
    private val newsDao: NewsDao
) {

    suspend fun getNews() = flow { emit(newsApiService.getNews()) }
    suspend fun getAllNews() = newsDao.getAllNews()
    fun getNewsByUUID(uuid:String) = newsDao.getNewsByUUID(uuid)
    fun saveNews(newsEntity: NewsEntity) = newsDao.insert(newsEntity)
    fun removeNews(newsEntity: NewsEntity) = newsDao.remove(newsEntity)

}