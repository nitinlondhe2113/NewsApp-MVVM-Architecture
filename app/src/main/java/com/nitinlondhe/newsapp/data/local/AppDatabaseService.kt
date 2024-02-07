package com.nitinlondhe.newsapp.data.local

import com.nitinlondhe.newsapp.data.local.entity.Article
import com.nitinlondhe.newsapp.data.local.entity.NewsSources
import kotlinx.coroutines.flow.Flow

class AppDatabaseService constructor(private val appDatabase: NewsAppDatabase) : DatabaseService {

    override fun getAllTopHeadlinesArticles(countryID: String): Flow<List<Article>> {
        return appDatabase.topHeadlinesDao().getTopHeadlinesArticles(countryID)
    }

    override fun deleteAndInsertAllTopHeadlinesArticles(
        articles: List<Article>,
        countryID: String
    ) {
        appDatabase.topHeadlinesDao().deleteAndInsertAllTopHeadlinesArticles(articles, countryID)
    }

    override fun getNewsSources(): Flow<List<NewsSources>> {
        return appDatabase.newsSourceDao().getSourcesNews()
    }

    override fun deleteAndInsertAllNewsSources(articles: List<NewsSources>) {
        appDatabase.newsSourceDao().deleteAndInsertAllSourceNews(articles)
    }

    override fun getSourceNewsByDB(sourceID: String): Flow<List<Article>> {
        return appDatabase.topHeadlinesDao().getSourceArticle(sourceID)
    }

    override fun deleteAllAndInsertAllSourceNews(articles: List<Article>, sourceID: String) {
        appDatabase.topHeadlinesDao().deleteAllAndInsertAllSourceArticles(articles, sourceID)
    }

    override fun getLanguageNews(languageID: String): Flow<List<Article>> {
        return appDatabase.topHeadlinesDao().getLanguageArticles(languageID)
    }

    override fun deleteAllAndInsertAllLanguageArticles(
        articles: List<Article>,
        languageID: String
    ) {
        appDatabase.topHeadlinesDao().deleteAllAndInsertAllLanguageArticles(articles, languageID)
    }
}