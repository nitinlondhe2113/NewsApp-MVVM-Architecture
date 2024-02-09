package com.nitinlondhe.newsapp.di.module

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

}