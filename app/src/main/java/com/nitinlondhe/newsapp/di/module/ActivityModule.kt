package com.nitinlondhe.newsapp.di.module

import com.nitinlondhe.newsapp.ui.country.CountryListAdapter
import com.nitinlondhe.newsapp.ui.language.LanguageListAdapter
import com.nitinlondhe.newsapp.ui.news.NewsListAdapter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@Module
@InstallIn(ActivityComponent::class)
class ActivityModule {

    @Provides
    fun provideNewsAdapter() = NewsListAdapter(ArrayList())

    @Provides
    fun provideCountryListAdapter() = CountryListAdapter(ArrayList())

    @Provides
    fun provideLanguageListAdapter() = LanguageListAdapter(ArrayList())
}