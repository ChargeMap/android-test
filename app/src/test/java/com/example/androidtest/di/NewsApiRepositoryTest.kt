package com.example.androidtest.di

import com.example.androidtest.Constants
import com.example.androidtest.Constants.CATEGORY
import com.example.androidtest.Constants.COUNTRY
import com.example.androidtest.data.network.NewsApi
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class NewsApiRepositoryTest {
    private lateinit var newsApi: NewsApi

    @Before
    fun setUp() {
        newsApi = Retrofit.Builder().baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(NewsApiModule.provideOkHttpClient())
            .build()
            .create(NewsApi::class.java)
    }

    @Test
    fun `get top headlines returns error`() {
        val response = runBlocking {
            newsApi.getCategoryTopHeadlines(country = "aaaaa", category = CATEGORY, pageSize = 10)
        }
        assertThat(response.body()).isNull()
    }

    @Test
    fun `get top headlines returns true`() {
        val response = runBlocking {
            newsApi.getCategoryTopHeadlines(country = COUNTRY, category = CATEGORY, pageSize = 10)
        }
        assertThat(response.body()).isNotNull()
    }
}