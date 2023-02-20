package com.abdulaziz.covidapp.data.remote

import com.abdulaziz.covidapp.CovidApp.Companion.API_KEY_NEWS
import com.abdulaziz.covidapp.data.model.NewsObject
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApiService {

    @GET("all")
    suspend fun getNews(
        @Query("api_token") api_token: String = API_KEY_NEWS,
        @Query("search") search: String = "coronavirus,covid,covid19",
        @Query("limit") limit: Int?=5,
        @Query("language") language: String = "en",
        @Query("sort") sort: String = "published_at",
        @Query("page") page: Int = 1,
    ): Response<NewsObject>
}