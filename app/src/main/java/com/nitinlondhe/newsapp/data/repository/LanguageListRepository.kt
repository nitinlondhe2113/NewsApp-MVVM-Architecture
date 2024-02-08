package com.nitinlondhe.newsapp.data.repository

import com.nitinlondhe.newsapp.data.model.Language
import com.nitinlondhe.newsapp.di.ActivityScope
import com.nitinlondhe.newsapp.utils.AppConstant
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

@ActivityScope
class LanguageListRepository @Inject constructor() {

    fun getLanguages(): Flow<List<Language>> {
        return flow { emit(AppConstant.LANGUAGES) }
    }

}
