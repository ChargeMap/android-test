package com.example.androidtest.data.network

import com.example.androidtest.Constants
import com.example.androidtest.models.NewsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {

    @GET("top-headlines")
    suspend fun getTopHeadlines(
        @Query("country") country: String,
        @Query("category") category: String,
        @Query("pageSize") pageSize: Int = Constants.PAGE_SIZE
    ): Response<NewsResponse>

    @GET("everything")
    suspend fun getEverythingBySearch(
        @Query("q") searchText: String,
        @Query("language") language: String,
        @Query("pageSize") pageSize: Int = Constants.PAGE_SIZE
    ): Response<NewsResponse>
}