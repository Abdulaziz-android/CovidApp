package com.abdulaziz.covidapp.data.local.dao

import androidx.room.*
import com.abdulaziz.covidapp.data.local.entitiy.NewsEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface NewsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(newsEntity: NewsEntity)

    @Delete
    fun remove(newsEntity: NewsEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertListNews(list: List<NewsEntity>)

    @Query("select * from newsentity")
    suspend fun getAllNews():List<NewsEntity>?

    @Query("select * from newsentity where isSaved = 1")
    fun getSavedNewsFlow(): Flow<List<NewsEntity>>

    @Query("select * from newsentity where viewCount > 0 order by viewCount desc")
    fun getRecentlyViewedNews():List<NewsEntity>?

    @Query("select max(viewCount) from newsentity")
    fun getLastViewCount():Int?

    @Query("select * from newsentity where isSaved = 1")
    fun getSavedNews():List<NewsEntity>?

    @Query("select * from newsentity where uuid = :uuid")
    fun getNewsByUUID(uuid:String):NewsEntity?


}