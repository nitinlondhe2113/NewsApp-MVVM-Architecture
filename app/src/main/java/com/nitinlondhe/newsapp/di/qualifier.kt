package com.nitinlondhe.newsapp.di

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.SOURCE)
annotation class ApplicationContext

@Qualifier
@Retention(AnnotationRetention.SOURCE)
annotation class ActivityContext

@Qualifier
@Retention(AnnotationRetention.SOURCE)
annotation class DatabaseName

@Qualifier
@Retention(AnnotationRetention.SOURCE)
annotation class NetworkAPIKey

@Qualifier
@Retention(AnnotationRetention.SOURCE)
annotation class BaseUrl