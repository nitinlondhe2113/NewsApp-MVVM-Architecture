package com.nitinlondhe.newsapp.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.nitinlondhe.newsapp.data.local.entity.Article
import kotlinx.coroutines.flow.Flow

@Dao
interface TopHeadlinesDao {

    @Transaction
    @Query("SELECT * FROM TopHeadlinesArticle WHERE country =:country")
    fun getTopHeadlinesArticles(country: String): Flow<List<Article>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTopHeadlineArticles(articles: List<Article>): List<Long>

    @Query("DELETE FROM TopHeadlinesArticle WHERE country = :country ")
    fun clearTopHeadlinesArticles(country: String)

    @Transaction
    fun deleteAndInsertAllTopHeadlinesArticles(
        articles: List<Article>, country: String
    ): List<Long> {
        clearTopHeadlinesArticles(country)
        return insertTopHeadlineArticles(articles)
    }

    @Query("SELECT * FROM TopHeadlinesArticle WHERE sourceId =:sourceId")
    fun getSourceArticle(sourceId: String): Flow<List<Article>>

    @Query("DELETE FROM TopHeadlinesArticle WHERE sourceId = :sourceId ")
    fun clearSourceArticles(sourceId: String)

    @Transaction
    fun deleteAllAndInsertAllSourceArticles(articles: List<Article>, sourceID: String): List<Long> {
        clearSourceArticles(sourceID)
        return insertTopHeadlineArticles(articles)
    }

    @Query("SELECT * FROM TopHeadlinesArticle WHERE language =:languageId")
    fun getLanguageArticles(languageId: String): Flow<List<Article>>

    @Query("DELETE FROM TopHeadlinesArticle WHERE language = :languageId ")
    fun clearLanguageArticles(languageId: String)

    @Transaction
    fun deleteAllAndInsertAllLanguageArticles(
        articles: List<Article>,
        languageId: String
    ): List<Long> {
        clearLanguageArticles(languageId)
        return insertTopHeadlineArticles(articles)
    }

}