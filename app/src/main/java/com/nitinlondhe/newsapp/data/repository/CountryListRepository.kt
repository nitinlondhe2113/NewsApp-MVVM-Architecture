package com.nitinlondhe.newsapp.data.repository

import com.nitinlondhe.newsapp.data.model.Country
import com.nitinlondhe.newsapp.di.ActivityScope
import com.nitinlondhe.newsapp.utils.AppConstant
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

@ActivityScope
class CountryListRepository @Inject constructor() {

    fun getCountry(): Flow<List<Country>> {
        return flow { emit(AppConstant.COUNTRIES) }
    }
}
