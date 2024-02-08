package com.nitinlondhe.newsapp.di.component

import com.nitinlondhe.newsapp.data.repository.CountryListRepository
import com.nitinlondhe.newsapp.data.repository.NewsRepository
import com.nitinlondhe.newsapp.data.repository.NewsSourceRepository
import com.nitinlondhe.newsapp.data.repository.OfflineTopHeadlineRepository
import com.nitinlondhe.newsapp.data.repository.PaginationTopHeadlineRepository
import com.nitinlondhe.newsapp.data.repository.TopHeadlineRepository
import com.nitinlondhe.newsapp.di.ActivityScope
import com.nitinlondhe.newsapp.di.module.ActivityModule
import com.nitinlondhe.newsapp.ui.country.CountryListActivity
import com.nitinlondhe.newsapp.ui.news.NewsListActivity
import com.nitinlondhe.newsapp.ui.offline.OfflineTopHeadlineActivity
import com.nitinlondhe.newsapp.ui.pagination.PaginationTopHeadlineActivity
import com.nitinlondhe.newsapp.ui.sources.NewsSourcesActivity
import com.nitinlondhe.newsapp.ui.topheadline.TopHeadlineActivity
import dagger.Component

@ActivityScope
@Component(dependencies = [ApplicationComponent::class], modules = [ActivityModule::class])
interface ActivityComponent {

    fun inject(activity: TopHeadlineActivity)

    fun inject(activity: OfflineTopHeadlineActivity)

    fun inject(activity: PaginationTopHeadlineActivity)

    fun inject(activity: NewsSourcesActivity)

    fun inject(activity: NewsListActivity)

    fun inject(activity: CountryListActivity)


    fun getTopHeadlineRepository(): TopHeadlineRepository

    fun getOfflineTopHeadlineRepository(): OfflineTopHeadlineRepository

    fun getPaginationTopHeadlineRepository(): PaginationTopHeadlineRepository

    fun getNewsSourceRepository(): NewsSourceRepository

    fun getNewsRepository(): NewsRepository

    fun getCountryListRepository(): CountryListRepository

}