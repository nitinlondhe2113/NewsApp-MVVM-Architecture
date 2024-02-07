package com.nitinlondhe.newsapp.data.api

import com.nitinlondhe.newsapp.data.model.topheadlines.TopHeadlinesResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface NetworkService {

    @GET("top-headlines")
    suspend fun getTopHeadlines(@Query("country") country: String, @Query("page") page: Int = 1, @Query("pageSize") pageSize: Int = 20): TopHeadlinesResponse

}