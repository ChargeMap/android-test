package com.example.androidtest.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.androidtest.data.db.dao.ArticleDao
import com.example.androidtest.data.db.entity.ArticleEntity


@Database(
    entities = [ArticleEntity::class],
    version = 2
)
@TypeConverters(Converter::class)
abstract class ArticleDatabase : RoomDatabase() {

    abstract fun getArticleDao(): ArticleDao
}