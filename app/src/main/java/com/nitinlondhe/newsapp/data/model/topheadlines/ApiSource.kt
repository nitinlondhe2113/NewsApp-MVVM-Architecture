package com.nitinlondhe.newsapp.data.model.topheadlines

import com.google.gson.annotations.SerializedName
import com.nitinlondhe.newsapp.data.local.entity.Source

data class ApiSource(
    @SerializedName("id") val id: String? = null,
    @SerializedName("name") val name: String = "",
)

fun ApiSource.toSourceEntity(): Source {
    return Source(id, name)
}