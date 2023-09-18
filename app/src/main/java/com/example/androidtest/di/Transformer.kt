package com.example.androidtest.di

import com.example.androidtest.data.db.entity.ArticleEntity
import com.example.androidtest.data.db.entity.SourceEntity
import com.example.androidtest.models.Article
import com.example.androidtest.models.Source

object Transformer {
    fun convertToArticleEntityList(articleList: List<Article>): List<ArticleEntity> {
        return articleList.map {
            ArticleEntity(
                author = it.author,
                content = it.content,
                source = convertSourceModelToSourceEntity(it.source),
                description = it.description,
                publishedAt = it.publishedAt,
                url = it.url,
                urlToImage = it.urlToImage,
                title = it.title
            )
        }
    }

    fun convertArticleModelToArticleEntity(article: Article): ArticleEntity {
        return ArticleEntity(
            author = article.author,
            content = article.content,
            source = convertSourceModelToSourceEntity(article.source),
            description = article.description,
            publishedAt = article.publishedAt,
            url = article.url,
            urlToImage = article.urlToImage,
            title = article.title

        )
    }

    private fun convertSourceModelToSourceEntity(source: Source?): SourceEntity? {
        source?.let {
            return SourceEntity(source.id, source.name)
        }

        return null
    }

    private fun convertSourceEntityToSourceModel(sourceEntity: SourceEntity?): Source? {
        sourceEntity?.let {
            return Source(sourceEntity.id, sourceEntity.name)
        }

        return null
    }
}