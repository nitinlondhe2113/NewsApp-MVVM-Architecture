package com.nitinlondhe.newsapp.di.component

import com.nitinlondhe.newsapp.di.ActivityScope
import com.nitinlondhe.newsapp.di.module.ActivityModule
import com.nitinlondhe.newsapp.ui.topheadline.TopHeadlineActivity
import dagger.Component

@ActivityScope
@Component(dependencies = [ApplicationComponent::class], modules = [ActivityModule::class])
interface ActivityComponent {

    fun inject(activity: TopHeadlineActivity)

}