package com.abdulaziz.covidapp.di.module

import android.content.Context
import androidx.room.Room
import com.abdulaziz.covidapp.data.local.AppDatabase
import com.abdulaziz.covidapp.data.local.dao.StatisticGlobalDao
import com.abdulaziz.covidapp.data.local.dao.NewsDao
import com.abdulaziz.covidapp.data.local.dao.StatisticsByCountryDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context:Context): AppDatabase {
        return Room.databaseBuilder(context, AppDatabase::class.java, "covid_database")
            .fallbackToDestructiveMigration()
            .allowMainThreadQueries()
            .build()
    }


    @Provides
    @Singleton
    fun provideNewsDao(appDatabase: AppDatabase): NewsDao = appDatabase.newsDao()

    @Provides
    @Singleton
    fun provideStatisticCountryDao(appDatabase: AppDatabase): StatisticsByCountryDao = appDatabase.statisticCountryDao()

    @Provides
    @Singleton
    fun provideStatisticGlobalDao(appDatabase: AppDatabase): StatisticGlobalDao = appDatabase.statisticGlobalDao()



}