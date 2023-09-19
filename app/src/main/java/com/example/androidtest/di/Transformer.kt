package com.example.androidtest.di

import com.example.androidtest.data.db.entity.ArticleEntity
import com.example.androidtest.data.db.entity.SourceEntity
import com.example.androidtest.models.Article
import com.example.androidtest.models.Source

object Transformer {
    fun convertToArticleEntityList(articleList: List<Article>): List<ArticleEntity> {
        return articleList.map {
            convertArticleModelToArticleEntity(it)
        }
    }

    fun convertArticleModelToArticleEntity(article: Article): ArticleEntity {
        return ArticleEntity(
            author = article.author.orEmpty(),
            content = article.content.orEmpty(),
            source = convertSourceModelToSourceEntity(article.source),
            description = article.description.orEmpty(),
            publishedAt = article.publishedAt.orEmpty(),
            url = article.url.orEmpty(),
            urlToImage = article.urlToImage.orEmpty(),
            title = article.title.orEmpty()
        )
    }

    private fun convertSourceModelToSourceEntity(source: Source?): SourceEntity? {
        source?.let {
            return SourceEntity(source.id, source.name)
        }

        return null
    }
}