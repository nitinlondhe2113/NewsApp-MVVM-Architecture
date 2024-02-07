package com.nitinlondhe.newsapp.di.component

import android.content.Context
import com.nitinlondhe.newsapp.NewsApplication
import com.nitinlondhe.newsapp.data.api.NetworkService
import com.nitinlondhe.newsapp.di.ApplicationContext
import com.nitinlondhe.newsapp.di.module.ApplicationModule
import com.nitinlondhe.newsapp.utils.DispatcherProvider
import com.nitinlondhe.newsapp.utils.NetworkHelper
import com.nitinlondhe.newsapp.utils.logger.Logger
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ApplicationModule::class])
interface ApplicationComponent {

    fun inject(application: NewsApplication)

    @ApplicationContext
    fun getApplicationContext(): Context

    fun getNetworkService(): NetworkService

    fun getNetworkHelper(): NetworkHelper

    fun getDispatcherProvider(): DispatcherProvider

    fun getLoggerProvider(): Logger

}