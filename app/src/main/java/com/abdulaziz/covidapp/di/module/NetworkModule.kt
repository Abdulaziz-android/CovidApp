package com.abdulaziz.covidapp.di.module

import com.abdulaziz.covidapp.BuildConfig
import com.abdulaziz.covidapp.data.remote.NewsApiService
import com.abdulaziz.covidapp.data.remote.StatisticsApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    @Singleton
    @Named("news_baseurl")
    fun provideNewsBaseUrl(): String = "https://api.thenewsapi.com/v1/news/"

    @Provides
    @Singleton
    @Named("statistics_baseurl")
    fun provideStatisticBaseUrl(): String =
        "https://api.covid19api.com/"

    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        if (BuildConfig.DEBUG) {
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        } else {
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.NONE
        }
        return httpLoggingInterceptor
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        val okHttpClient = OkHttpClient.Builder()
        okHttpClient.addInterceptor(httpLoggingInterceptor)
        return okHttpClient.build()
    }

    @Provides
    @Singleton
    @Named("news_retrofit")
    fun provideNewsRetrofit(
        @Named("news_baseurl") base_url: String,
        okHttpClient: OkHttpClient
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(base_url)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }

    @Provides
    @Singleton
    @Named("statistics_retrofit")
    fun provideStatisticsRetrofit(
        @Named("statistics_baseurl") base_url: String,
        okHttpClient: OkHttpClient
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(base_url)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }

    @Provides
    @Singleton
    fun provideNewsApiService(@Named("news_retrofit") retrofit: Retrofit): NewsApiService =
        retrofit.create(NewsApiService::class.java)


    @Provides
    @Singleton
    fun provideStatisticApiService(@Named("statistics_retrofit") retrofit: Retrofit): StatisticsApiService =
        retrofit.create(StatisticsApiService::class.java)

}