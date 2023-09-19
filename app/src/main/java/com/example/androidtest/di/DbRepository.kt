package com.example.androidtest.di

import com.example.androidtest.data.db.ArticleDatabase
import com.example.androidtest.data.db.entity.ArticleEntity
import com.example.androidtest.models.Article
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DbRepository @Inject constructor(private val articleDatabase: ArticleDatabase) {
    suspend fun insertArticles(articleList: List<Article>) {
        articleDatabase.getArticleDao().insert(Transformer.convertToArticleEntityList(articleList))
    }

    suspend fun clearTable() {
        articleDatabase.getArticleDao().clearTable()
    }

    fun getAllArticles(): Flow<List<ArticleEntity>> {
        return articleDatabase.getArticleDao().getAllArticles()
    }

}