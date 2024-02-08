package com.nitinlondhe.newsapp.di.module

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.nitinlondhe.newsapp.data.repository.PaginationTopHeadlineRepository
import com.nitinlondhe.newsapp.data.repository.TopHeadlineRepository
import com.nitinlondhe.newsapp.di.ActivityContext
import com.nitinlondhe.newsapp.ui.base.ViewModelProviderFactory
import com.nitinlondhe.newsapp.ui.pagination.PaginationTopHeadlineAdapter
import com.nitinlondhe.newsapp.ui.pagination.PaginationTopHeadlineViewModel
import com.nitinlondhe.newsapp.ui.topheadline.TopHeadlineAdapter
import com.nitinlondhe.newsapp.ui.topheadline.TopHeadlineViewModel
import com.nitinlondhe.newsapp.utils.DispatcherProvider
import com.nitinlondhe.newsapp.utils.NetworkHelper
import com.nitinlondhe.newsapp.utils.logger.Logger
import dagger.Module
import dagger.Provides

@Module
class ActivityModule(private val activity: AppCompatActivity) {

    @ActivityContext
    @Provides
    fun provideContext(): Context {
        return activity
    }

    @Provides
    fun provideTopHeadLinesViewModel(
        topHeadlineRepository: TopHeadlineRepository,
        networkHelper: NetworkHelper,
        dispatcherProvider: DispatcherProvider,
        logger: Logger
    ): TopHeadlineViewModel {
        return ViewModelProvider(activity, ViewModelProviderFactory(TopHeadlineViewModel::class) {
            TopHeadlineViewModel(
                topHeadlineRepository,dispatcherProvider, networkHelper, logger
            )
        })[TopHeadlineViewModel::class.java]
    }

    @Provides
    fun providePaginationTopHeadLinesViewModel(
        paginationTopHeadlineRepository: PaginationTopHeadlineRepository,
        dispatcherProvider: DispatcherProvider
    ): PaginationTopHeadlineViewModel {
        return ViewModelProvider(activity,
            ViewModelProviderFactory(PaginationTopHeadlineViewModel::class) {
                PaginationTopHeadlineViewModel(
                    paginationTopHeadlineRepository, dispatcherProvider
                )
            })[PaginationTopHeadlineViewModel::class.java]
    }


    @Provides
    fun provideTopHeadlineAdapter() = TopHeadlineAdapter(ArrayList())

    @Provides
    fun providePaginationTopHeadlineAdapter() = PaginationTopHeadlineAdapter()
}