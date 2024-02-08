package com.nitinlondhe.newsapp.data.repository

import com.nitinlondhe.newsapp.data.api.NetworkService
import com.nitinlondhe.newsapp.data.local.DatabaseService
import com.nitinlondhe.newsapp.data.local.entity.Article
import com.nitinlondhe.newsapp.data.model.topheadlines.ApiArticle
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@ViewModelScoped
class OfflineTopHeadlineRepository @Inject constructor(
    private val networkService: NetworkService,
    private val databaseService: DatabaseService
) {

    fun getTopHeadlinesArticles(countryID: String): Flow<List<ApiArticle>> {
        return flow { emit(networkService.getTopHeadlines(countryID)) }
            .map {
                it.apiArticles
            }
    }

    fun deleteAndInsertAllTopHeadlinesArticles(articles: List<Article>, country: String) {
        databaseService.deleteAndInsertAllTopHeadlinesArticles(articles, country)
    }

    fun getTopHeadlinesArticlesFromDB(countryID: String): Flow<List<Article>> {
        return databaseService.getAllTopHeadlinesArticles(countryID)
    }
}