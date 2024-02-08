package com.nitinlondhe.newsapp.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nitinlondhe.newsapp.data.local.entity.Article
import com.nitinlondhe.newsapp.data.repository.SearchRepository
import com.nitinlondhe.newsapp.ui.base.UiState
import com.nitinlondhe.newsapp.utils.AppConstant.DEBOUNCE_TIMEOUT
import com.nitinlondhe.newsapp.utils.AppConstant.MIN_SEARCH_CHAR
import com.nitinlondhe.newsapp.utils.DispatcherProvider
import com.nitinlondhe.newsapp.utils.NetworkHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchRepository: SearchRepository,
    private val dispatcherProvider: DispatcherProvider,
    private val networkHelper: NetworkHelper,
) : ViewModel() {

    private val _searchUiState = MutableStateFlow<UiState<List<Article>>>(UiState.Loading)

    val searchUiState: StateFlow<UiState<List<Article>>> = _searchUiState

    private fun checkInternetConnection(): Boolean = networkHelper.isNetworkConnected()

    private val query = MutableStateFlow("")

    init {
        createNewsFlow()
    }

    fun searchNews(searchQuery: String) {
        query.value = searchQuery
    }

    @OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
    fun createNewsFlow() {
        viewModelScope.launch {
            query.debounce(DEBOUNCE_TIMEOUT).filter {
                if (it.isNotEmpty() && it.length >= MIN_SEARCH_CHAR) {
                    return@filter true
                } else {
                    _searchUiState.value = UiState.Success(emptyList())
                    return@filter false
                }
            }
                .distinctUntilChanged().flatMapLatest {
                    _searchUiState.value = UiState.Loading
                    return@flatMapLatest searchRepository.getNewsByQueries(it).catch { e ->
                        // handle error properly
                        _searchUiState.value = UiState.Error(e.toString())
                    }
                }
                .flowOn(dispatcherProvider.io)
                .collect {
                    if (!checkInternetConnection() && it.isEmpty()) {
                        _searchUiState.value = UiState.Error("Data Not found.")
                    } else {
                        _searchUiState.value = UiState.Success(it)
                    }
                }
        }
    }
}
