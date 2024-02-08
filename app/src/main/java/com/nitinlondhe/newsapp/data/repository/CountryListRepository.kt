package com.nitinlondhe.newsapp.data.repository

import com.nitinlondhe.newsapp.data.model.Country
import com.nitinlondhe.newsapp.utils.AppConstant
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

@ViewModelScoped
class CountryListRepository @Inject constructor() {

    fun getCountry(): Flow<List<Country>> {
        return flow { emit(AppConstant.COUNTRIES) }
    }
}
