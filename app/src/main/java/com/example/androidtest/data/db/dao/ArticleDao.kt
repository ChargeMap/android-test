package com.example.androidtest.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.androidtest.data.db.entity.ArticleEntity

@Dao
interface ArticleDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(article: List<ArticleEntity>)

    @Query("SELECT * FROM articles WHERE country IN (:countries)")
    suspend fun getArticlesByCountry(countries: List<String>): List<ArticleEntity>

    @Query("SELECT * FROM articles WHERE id=:id")
    suspend fun getArticleById(id: Int): ArticleEntity

    @Query("DELETE FROM articles WHERE country =:country")
    fun deleteByCountry(country: String)
}