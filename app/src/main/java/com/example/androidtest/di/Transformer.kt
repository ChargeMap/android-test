package com.example.androidtest.di

import com.example.androidtest.data.db.entity.ArticleEntity
import com.example.androidtest.data.db.entity.SourceEntity
import com.example.androidtest.models.Article
import com.example.androidtest.models.Source
import com.example.newsfeed.presentation.util.DateUtil

object Transformer {
    fun convertToArticleEntityList(articleList: List<Article>): List<ArticleEntity> {
        return articleList.map {
            convertArticleModelToArticleEntity(it)
        }
    }

    private fun convertArticleModelToArticleEntity(article: Article): ArticleEntity {
        return ArticleEntity(
            author = article.author.orEmpty(),
            content = article.content.orEmpty(),
            source = convertSourceModelToSourceEntity(article.source),
            description = article.description.orEmpty(),
            publishedAt = DateUtil.formatDate(
                article.publishedAt.orEmpty(),
                DateUtil.DATE_FORMAT_FULL
            ),
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