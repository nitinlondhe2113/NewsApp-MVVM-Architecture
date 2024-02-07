package com.nitinlondhe.newsapp.di.component

import com.nitinlondhe.newsapp.di.ActivityScope
import com.nitinlondhe.newsapp.di.module.ActivityModule
import dagger.Component

@ActivityScope
@Component(dependencies = [ApplicationComponent::class], modules = [ActivityModule::class])
interface ActivityComponent {

}