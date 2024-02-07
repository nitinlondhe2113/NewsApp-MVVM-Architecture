package com.nitinlondhe.newsapp.ui.topheadline

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nitinlondhe.newsapp.data.model.topheadlines.Article
import com.nitinlondhe.newsapp.data.repository.TopHeadlineRepository
import com.nitinlondhe.newsapp.ui.base.UiState
import com.nitinlondhe.newsapp.utils.AppConstant
import com.nitinlondhe.newsapp.utils.DispatcherProvider
import com.nitinlondhe.newsapp.utils.NetworkHelper
import com.nitinlondhe.newsapp.utils.logger.Logger
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

class TopHeadlineViewModel @Inject constructor(
    private val topHeadlineRepository: TopHeadlineRepository,
    private val dispatcherProvider: DispatcherProvider,
    private val networkHelper: NetworkHelper,
    private val logger: Logger
) : ViewModel() {

    private val _topHeadlineUiState = MutableStateFlow<UiState<List<Article>>>(UiState.Loading)

    val topHeadlineUiState: StateFlow<UiState<List<Article>>> = _topHeadlineUiState

    private fun checkInternetConnection(): Boolean = networkHelper.isNetworkConnected()

    init {
        startFetchingArticles()
    }

    fun startFetchingArticles() {
        if (checkInternetConnection()) {
            fetchArticles()
        } else {
            _topHeadlineUiState.value = UiState.Error("Data Not found.")
        }
    }

    private fun fetchArticles() {
        viewModelScope.launch(dispatcherProvider.main) {
            topHeadlineRepository.getTopHeadlinesArticles(AppConstant.COUNTRY)
                .flowOn(dispatcherProvider.io)
                .catch { e ->
                    _topHeadlineUiState.value = UiState.Error(e.toString())
                }.collect {
                _topHeadlineUiState.value = UiState.Success(it)
                logger.d("TopHeadlineViewModel", "Success")
            }
        }
    }
}