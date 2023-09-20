package com.example.androidtest.di

import android.util.Log
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
    private val TAG = NewsApiRepository::class.simpleName

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
                try {
                    dbRepository.insertArticles(response.body()?.articles.orEmpty().reversed())
                } catch (e: Exception) {
                    e.message?.let { Log.e(TAG, it) }
                }

            }.onFailure { it.message?.let { message -> Log.e(TAG, message) } }
        }
    }
}