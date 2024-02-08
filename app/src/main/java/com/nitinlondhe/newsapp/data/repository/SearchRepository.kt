package com.nitinlondhe.newsapp.data.repository

import com.nitinlondhe.newsapp.data.api.NetworkService
import com.nitinlondhe.newsapp.data.local.entity.Article
import com.nitinlondhe.newsapp.data.model.topheadlines.toArticleEntity
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@ViewModelScoped
class SearchRepository @Inject constructor(private val networkService: NetworkService) {

    fun getNewsByQueries(query: String): Flow<List<Article>> {
        return flow {
            emit(networkService.getNewsByQueries(query))
        }.map {
            it.apiArticles.map { apiArticle -> apiArticle.toArticleEntity(query) }
        }
    }
}
