package com.nitinlondhe.newsapp.ui.pagination

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.nitinlondhe.newsapp.data.model.topheadlines.ApiArticle
import com.nitinlondhe.newsapp.data.repository.PaginationTopHeadlineRepository
import com.nitinlondhe.newsapp.utils.DispatcherProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PaginationTopHeadlineViewModel @Inject constructor(
    private val paginationTopHeadlineRepository: PaginationTopHeadlineRepository,
    private val dispatcherProvider: DispatcherProvider
) : ViewModel() {

    private val _topHeadlineUiState = MutableStateFlow<PagingData<ApiArticle>>(value = PagingData.empty())

    val topHeadlineUiState: StateFlow<PagingData<ApiArticle>> = _topHeadlineUiState

    init {
        startFetchingArticles()
    }

    private fun startFetchingArticles() {
        viewModelScope.launch(dispatcherProvider.main) {
            paginationTopHeadlineRepository.getTopHeadlinesArticles()
                .flowOn(dispatcherProvider.io)
                .catch { e -> }
                .collect {
                    _topHeadlineUiState.value = it
                }
        }
    }

}