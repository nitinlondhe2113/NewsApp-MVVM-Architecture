package com.nitinlondhe.newsapp.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.nitinlondhe.newsapp.data.local.entity.NewsSources
import kotlinx.coroutines.flow.Flow

@Dao
interface SourceDao {

    @Transaction
    @Query("SELECT * FROM Sources")
    fun getSourcesNews(): Flow<List<NewsSources>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertSourcesNews(newsSource: List<NewsSources>): List<Long>

    @Query("DELETE FROM Sources")
    fun clearSourcesNews(): Int

    @Transaction
    fun deleteAndInsertAllSourceNews(newsSource: List<NewsSources>): List<Long> {
        clearSourcesNews()
        return insertSourcesNews(newsSource)
    }
}