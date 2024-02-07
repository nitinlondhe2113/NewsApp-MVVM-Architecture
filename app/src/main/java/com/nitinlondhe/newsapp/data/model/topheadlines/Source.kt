package com.nitinlondhe.newsapp.data.model.topheadlines

import com.google.gson.annotations.SerializedName

data class Source(
    @SerializedName("id") val id: String? = null,
    @SerializedName("name") val name: String = "",
)