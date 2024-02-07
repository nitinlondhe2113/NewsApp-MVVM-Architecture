package com.nitinlondhe.newsapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.nitinlondhe.newsapp.data.local.dao.SourceDao
import com.nitinlondhe.newsapp.data.local.dao.TopHeadlinesDao
import com.nitinlondhe.newsapp.data.local.entity.Article
import com.nitinlondhe.newsapp.data.local.entity.NewsSources

@Database(
    entities = [Article::class, NewsSources::class],
    version = 1,
    exportSchema = false
)
abstract class NewsAppDatabase : RoomDatabase() {

    abstract fun topHeadlinesDao(): TopHeadlinesDao

    abstract fun newsSourceDao(): SourceDao

}