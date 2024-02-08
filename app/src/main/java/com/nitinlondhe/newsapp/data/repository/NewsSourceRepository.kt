package com.nitinlondhe.newsapp.data.repository

import com.nitinlondhe.newsapp.data.api.NetworkService
import com.nitinlondhe.newsapp.data.local.DatabaseService
import com.nitinlondhe.newsapp.data.local.entity.NewsSources
import com.nitinlondhe.newsapp.data.model.newssources.asSource
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@ViewModelScoped
class NewsSourceRepository @Inject constructor(
    private val networkService: NetworkService,
    private val databaseService: DatabaseService
) {

    @OptIn(ExperimentalCoroutinesApi::class)
    fun getNewsSources(): Flow<List<NewsSources>> {
        return flow { emit(networkService.getNewsSources()) }
            .map {
                it.newsSource.map { apiSource -> apiSource.asSource() }
            }.flatMapConcat { apiSource ->
                flow { emit(databaseService.deleteAndInsertAllNewsSources((apiSource))) }
            }.flatMapConcat {
                databaseService.getNewsSources()
            }
    }

    fun getNewsSourcesFromDB(): Flow<List<NewsSources>> {
        return databaseService.getNewsSources()

    }

}