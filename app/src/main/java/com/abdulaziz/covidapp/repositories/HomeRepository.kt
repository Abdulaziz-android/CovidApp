package com.abdulaziz.covidapp.repositories

import com.abdulaziz.covidapp.data.local.dao.NewsDao
import com.abdulaziz.covidapp.data.local.entitiy.NewsEntity
import com.abdulaziz.covidapp.data.remote.NewsApiService
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class HomeRepository @Inject constructor(
    private val newsApiService: NewsApiService,
    private val newsDao: NewsDao
) {

    suspend fun getNews() = flow { emit(newsApiService.getNews()) }
    suspend fun getAllNews() = newsDao.getAllNews()
    suspend fun saveNewsList(list: List<NewsEntity>) = newsDao.insertListNews(list)

}