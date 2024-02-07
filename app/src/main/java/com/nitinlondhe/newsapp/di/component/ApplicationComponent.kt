package com.nitinlondhe.newsapp.di.component

import android.content.Context
import com.nitinlondhe.newsapp.NewsApplication
import com.nitinlondhe.newsapp.di.ApplicationContext
import com.nitinlondhe.newsapp.di.module.ApplicationModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ApplicationModule::class])
interface ApplicationComponent {

    fun inject(application: NewsApplication)

    @ApplicationContext
    fun getApplicationContext(): Context

}