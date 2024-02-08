package com.nitinlondhe.newsapp.di.module

import com.nitinlondhe.newsapp.ui.country.CountryListAdapter
import com.nitinlondhe.newsapp.ui.language.LanguageListAdapter
import com.nitinlondhe.newsapp.ui.news.NewsListAdapter
import com.nitinlondhe.newsapp.ui.pagination.PaginationTopHeadlineAdapter
import com.nitinlondhe.newsapp.ui.sources.NewsSourceAdapter
import com.nitinlondhe.newsapp.ui.topheadline.TopHeadlineAdapter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@Module
@InstallIn(ActivityComponent::class)
class ActivityModule {

    @Provides
    fun provideTopHeadlineAdapter() = TopHeadlineAdapter(ArrayList())

    @Provides
    fun providePaginationTopHeadlineAdapter() = PaginationTopHeadlineAdapter()

    @Provides
    fun provideNewsSourceAdapter() = NewsSourceAdapter(ArrayList())

    @Provides
    fun provideNewsAdapter() = NewsListAdapter(ArrayList())

    @Provides
    fun provideCountryListAdapter() = CountryListAdapter(ArrayList())

    @Provides
    fun provideLanguageListAdapter() = LanguageListAdapter(ArrayList())
}