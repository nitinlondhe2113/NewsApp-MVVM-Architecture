package com.nitinlondhe.newsapp.data.model.topheadlines

import com.google.gson.annotations.SerializedName

data class TopHeadlinesResponse(
    @SerializedName("status") val status: String = "",
    @SerializedName("totalResults") val totalResults: Int = 0,
    @SerializedName("articles") val apiArticles: List<ApiArticle> = ArrayList(),
)