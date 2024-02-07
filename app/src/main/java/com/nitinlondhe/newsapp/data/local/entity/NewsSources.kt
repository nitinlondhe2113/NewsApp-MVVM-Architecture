package com.nitinlondhe.newsapp.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Sources")
data class NewsSources(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") val id: Int = 0,
    @ColumnInfo(name = "sourceId") val sourceId: String = "",
    @ColumnInfo(name = "name") val name: String = "",
    @ColumnInfo(name = "description") val description: String = "",
    @ColumnInfo(name = "url") val url: String = "",
    @ColumnInfo(name = "category") val category: String = "",
    @ColumnInfo(name = "language") val language: String = "",
    @ColumnInfo(name = "country") val country: String = "",
)