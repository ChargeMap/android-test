package com.example.androidtest.di

import com.example.androidtest.data.db.ArticleDatabase
import com.example.androidtest.data.db.entity.ArticleEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class DbRepository @Inject constructor(private val articleDatabase: ArticleDatabase) {
    suspend fun insertArticles(articleList: List<ArticleEntity>) {
        articleDatabase.getArticleDao().insert(articleList)
    }

    fun clearTable() = articleDatabase.clearAllTables()

    fun getAllArticlesByCountry(country: String): Flow<List<ArticleEntity>> = flow {
        emit(articleDatabase.getArticleDao().getArticlesByCountry(country).sortedByDescending {
            it.publishedAt
        })
    }

    fun getArticleById(id: Int): Flow<ArticleEntity> = flow {
        emit(articleDatabase.getArticleDao().getArticleById(id))
    }

}