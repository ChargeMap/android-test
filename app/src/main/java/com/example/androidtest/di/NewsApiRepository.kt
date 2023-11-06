package com.example.androidtest.di

import com.example.androidtest.Constants.PAGE_SIZE
import com.example.androidtest.data.db.entity.ArticleEntity
import com.example.androidtest.data.network.NewsApi
import com.example.androidtest.util.Transformer
import com.example.androidtest.viewmodel.NewsFeedViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.withContext
import javax.inject.Inject

class NewsApiRepository @Inject constructor(
    private val newsApi: NewsApi
) {
    private suspend fun getTopHeadlines(country: String, category: String): List<ArticleEntity>? {
        val articleList = withContext(Dispatchers.IO) {
            try {
                newsApi.getTopHeadlines(
                    country = country,
                    category = category,
                    pageSize = PAGE_SIZE
                )
            } catch (exc: Exception) {
                throw exc
            }
        }.body()?.articles?.filterNot { article -> article.title == "[Removed]" }

        return articleList?.let { Transformer.convertToArticleEntityList(it, country) }
    }

    suspend fun getMultipleTopHeadlines(filters: NewsFeedViewModel.NewsFilters): List<ArticleEntity> {
        val filledList = emptyList<ArticleEntity>().toMutableList()

        try {
            coroutineScope {
                filters.categories.forEach { category ->
                    filters.country.forEach { country ->
                        val categoryHeadLinesDeferred =
                            async { getTopHeadlines(country = country, category = category) }


                        val categoryHeadLines = categoryHeadLinesDeferred.await()
                        categoryHeadLines?.let {
                            filledList.addAll(it)
                        }
                    }
                }
            }
        } catch (exc: Exception) {
            throw exc
        }

        return filledList.sortedByDescending { it.publishedAt }
    }
}