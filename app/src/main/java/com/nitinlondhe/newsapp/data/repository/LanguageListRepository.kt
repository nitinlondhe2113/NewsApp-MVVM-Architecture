package com.nitinlondhe.newsapp.data.repository

import com.nitinlondhe.newsapp.data.model.Language
import com.nitinlondhe.newsapp.utils.AppConstant
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

@ViewModelScoped
class LanguageListRepository @Inject constructor() {

    fun getLanguages(): Flow<List<Language>> {
        return flow { emit(AppConstant.LANGUAGES) }
    }

}
