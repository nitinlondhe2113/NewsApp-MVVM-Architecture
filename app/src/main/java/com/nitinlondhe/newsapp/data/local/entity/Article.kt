package com.nitinlondhe.newsapp.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "TopHeadlinesArticle")
data class Article(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "article_id") val id: Int = 0,
    @ColumnInfo(name = "title") val title: String = "",
    @ColumnInfo(name = "description") val description: String?,
    @ColumnInfo(name = "url") val url: String = "",
    @ColumnInfo(name = "urlToImage") val imageUrl: String? = "",
    @ColumnInfo(name = "country") val country: String = "",
    @ColumnInfo(name = "language") val language: String = "",
    @Embedded val source: Source
)