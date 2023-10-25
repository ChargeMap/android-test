package com.example.androidtest.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "articles")
data class ArticleEntity(
    @PrimaryKey
    var id: Int,
    var country: String,
    val author: String,
    val content: String,
    val description: String,
    val publishedAt: String,
    val source: SourceEntity?,
    val title: String,
    val url: String,
    val urlToImage: String
)
