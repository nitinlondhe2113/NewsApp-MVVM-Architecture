package com.nitinlondhe.newsapp.ui.news

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nitinlondhe.newsapp.data.local.entity.Article
import com.nitinlondhe.newsapp.data.repository.NewsRepository
import com.nitinlondhe.newsapp.ui.base.UiState
import com.nitinlondhe.newsapp.utils.DispatcherProvider
import com.nitinlondhe.newsapp.utils.NetworkHelper
import com.nitinlondhe.newsapp.utils.logger.Logger
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.zip
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsListViewModel @Inject constructor(
    private val newsRepository: NewsRepository,
    private val logger: Logger,
    private val dispatcherProvider: DispatcherProvider,
    private val networkHelper: NetworkHelper,
) : ViewModel() {

    private val _newsUiState = MutableStateFlow<UiState<List<Article>>>(UiState.Loading)

    val newUiState: StateFlow<UiState<List<Article>>> = _newsUiState

    private fun checkInternetConnection(): Boolean = networkHelper.isNetworkConnected()

    fun fetchNewsBySources(sourceId: String) {
        if (checkInternetConnection()) fetchNewsBySourcesByNetwork(sourceId)
        else fetchNewsBySourcesByDB(sourceId)
    }

    private fun fetchNewsBySourcesByNetwork(sourceId: String) {
        viewModelScope.launch(dispatcherProvider.main) {
            newsRepository.getNewsBySources(sourceId)
                .flowOn(dispatcherProvider.io)
                .catch { e ->
                    _newsUiState.value = UiState.Error(e.toString())
                }.collect {
                    _newsUiState.value = UiState.Success(it)
                    logger.d("NewsViewModel", it.toString())
                }
        }
    }

    private fun fetchNewsBySourcesByDB(sourceId: String) {
        viewModelScope.launch(dispatcherProvider.main) {
            newsRepository.getNewsBySourceByDB(sourceId)
                .flowOn(dispatcherProvider.io)
                .catch { e ->
                    _newsUiState.value = UiState.Error(e.toString())
                }.collect {
                    if(!checkInternetConnection() && it.isEmpty()) {
                        _newsUiState.value = UiState.Error("Data Not found.")
                    } else {
                        _newsUiState.value = UiState.Success(it)
                    }
                }
        }
    }

    fun fetchNewsByCountry(countryId: String) {
        if (checkInternetConnection()) fetchNewsByCountryByNetwork(countryId)
        else fetchNewsByCountryByDB(countryId)
    }

    private fun fetchNewsByCountryByNetwork(countryId: String) {
        viewModelScope.launch(dispatcherProvider.main) {
            newsRepository.getNewsByCountry(countryId)
                .flowOn(dispatcherProvider.io)
                .catch { e ->
                    _newsUiState.value = UiState.Error(e.toString())
                }.collect {
                    if(!checkInternetConnection() && it.isEmpty()) {
                        _newsUiState.value = UiState.Error("Data Not found.")
                    } else {
                        _newsUiState.value = UiState.Success(it)
                    }
                }
        }
    }

    private fun fetchNewsByCountryByDB(countryId: String) {
        viewModelScope.launch(dispatcherProvider.main) {
            newsRepository.getNewsByCountryByDB(countryId)
                .flowOn(dispatcherProvider.io)
                .catch { e ->
                    _newsUiState.value = UiState.Error(e.toString())
                }.collect {
                    if(!checkInternetConnection() && it.isEmpty()) {
                        _newsUiState.value = UiState.Error("Data Not found.")
                    } else {
                        _newsUiState.value = UiState.Success(it)
                    }
                }
        }
    }

    fun fetchNewsByLanguage(languageId: String) {
        val separatedValues: List<String> = languageId.split(",")
        val languageIdFirst = separatedValues.getOrNull(0)
        val languageIdSecond = separatedValues.getOrNull(1)

        if (checkInternetConnection()) fetchNewsByLanguageByNetwork(languageIdFirst.toString(), languageIdSecond.toString())
        else fetchNewsByLanguageByDB(languageIdFirst.toString(), languageIdSecond.toString())
    }

    private fun fetchNewsByLanguageByNetwork(languageIdFirst: String, languageIdSecond: String) {
        viewModelScope.launch(dispatcherProvider.main) {
            newsRepository.getNewsByLanguage(languageId = languageIdFirst)
                .zip(newsRepository.getNewsByLanguage(languageId = languageIdSecond)) { firstLangRes, secLangRes ->
                    return@zip Pair(firstLangRes, secLangRes)
                }
                .flowOn(dispatcherProvider.io)
                .catch { e ->
                    _newsUiState.value = UiState.Error(e.toString())
                }.collect {
                    fetchNewsByLanguageByDB(languageIdFirst, languageIdSecond)
                }
        }
    }

    private fun fetchNewsByLanguageByDB(languageCode: String, secLanguageCode: String) {
        viewModelScope.launch(dispatcherProvider.main) {
            newsRepository.getNewsByLanguageByDB(languageCode)
                .zip(newsRepository.getNewsByLanguageByDB(secLanguageCode)) { firstLangData, secLangData ->
                    val articles = mutableListOf<Article>()
                    articles.addAll(firstLangData)
                    articles.addAll(secLangData)
                    return@zip articles
                }
                .flowOn(dispatcherProvider.io)
                .catch { e ->
                    _newsUiState.value = UiState.Error(e.toString())
                }.collect {
                    if(!checkInternetConnection() && it.isEmpty()) {
                        _newsUiState.value = UiState.Error("Data Not found.")
                    } else {
                        _newsUiState.value = UiState.Success(it)
                        logger.d("NewsViewModel", "Success")
                    }
                }
        }
    }


}