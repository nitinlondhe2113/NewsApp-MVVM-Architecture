package com.nitinlondhe.newsapp.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.nitinlondhe.newsapp.data.api.NetworkService
import com.nitinlondhe.newsapp.data.model.topheadlines.ApiArticle
import com.nitinlondhe.newsapp.utils.AppConstant.PAGE_SIZE
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@ViewModelScoped
class PaginationTopHeadlineRepository @Inject constructor(private val networkService: NetworkService) {

    fun getTopHeadlinesArticles(): Flow<PagingData<ApiArticle>> {
        return Pager(config = PagingConfig(pageSize = PAGE_SIZE),
            pagingSourceFactory = { TopHeadlinePagingSource(networkService)
            }).flow
    }
}