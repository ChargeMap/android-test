package com.example.androidtest.di

import com.example.androidtest.Constants.CATEGORY
import com.example.androidtest.Constants.COUNTRY
import com.example.androidtest.Constants.PAGE_SIZE
import com.example.androidtest.data.network.NewsApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class NewsApiRepository @Inject constructor(
    private val newsApi: NewsApi,
    private val dbRepository: DbRepository
) {

    suspend fun getTopHeadlines(
        country: String = COUNTRY,
        category: String = CATEGORY,
        pageSize: Int = PAGE_SIZE
    ) {
        withContext(Dispatchers.IO) {
            runCatching {
                newsApi.getTopHeadlines(
                    country = country,
                    category = category,
                    pageSize = pageSize
                )
            }.onSuccess { response ->
                dbRepository.insertArticles(response.body()?.articles.orEmpty().reversed())
            }.onFailure { println(it.message) }
        }
    }
}