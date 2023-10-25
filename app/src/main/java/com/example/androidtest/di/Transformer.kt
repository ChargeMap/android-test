package com.example.androidtest.di

import com.example.androidtest.data.db.entity.ArticleEntity
import com.example.androidtest.data.db.entity.SourceEntity
import com.example.androidtest.models.Article
import com.example.androidtest.models.Source
import com.example.androidtest.util.DateUtil

object Transformer {
    fun convertToArticleEntityList(articleList: List<Article>, country: String): List<ArticleEntity> {
        return articleList.map {
            convertArticleModelToArticleEntity(it, country)
        }.sortedByDescending { it.publishedAt }
    }

    private fun convertArticleModelToArticleEntity(article: Article, country: String): ArticleEntity {
        return ArticleEntity(
            id = article.title.hashCode(),
            country = country,
            author = article.author.orEmpty(),
            content = article.content.orEmpty(),
            description = article.description.orEmpty(),
            publishedAt = DateUtil.formatDate(
                article.publishedAt.orEmpty(),
                DateUtil.DATE_FORMAT_FULL
            ),
            source = convertSourceModelToSourceEntity(article.source),
            title = article.title.orEmpty(),
            url = article.url.orEmpty(),
            urlToImage = article.urlToImage.orEmpty()
        )
    }

    private fun convertSourceModelToSourceEntity(source: Source?): SourceEntity? {
        source?.let {
            return SourceEntity(source.id, source.name)
        }

        return null
    }
}