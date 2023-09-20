package com.example.androidtest.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidtest.data.db.entity.ArticleEntity
import com.example.androidtest.di.DbRepository
import com.example.androidtest.di.NewsApiRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class NewsFeedViewModel @Inject constructor(
    private val apiRepository: NewsApiRepository,
    private val dbRepository: DbRepository
) :
    ViewModel() {
    private val TAG = NewsFeedViewModel::class.simpleName

    private val _topHeadlines = MutableStateFlow<List<ArticleEntity>>(emptyList())
    val topHeadlines: StateFlow<List<ArticleEntity>> = _topHeadlines

    private val _refreshing = MutableStateFlow(false)
    val refreshing: StateFlow<Boolean> = _refreshing

    private val _article = MutableStateFlow<ArticleEntity?>(null)
    val article: StateFlow<ArticleEntity?> = _article

    init {
        viewModelScope.launch(Dispatchers.IO) {
            getTopHeadLines()
        }
    }

    suspend fun getTopHeadLines() {
        try {
            apiRepository.getTopHeadlines()
            dbRepository.getAllArticles().collect {
                _topHeadlines.value = it
            }
        } catch (e: Exception) {
            e.message?.let { Log.e(TAG, it) }
        }
    }

    suspend fun refresh() {
        _refreshing.emit(true)
        viewModelScope.launch(Dispatchers.IO) {
            try {
                dbRepository.clearTable()
                getTopHeadLines()
            } catch (e: Exception) {
                e.message?.let { Log.e(TAG, it) }
            }

        }.also { _refreshing.emit(false) }
    }

    suspend fun getArticleById(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                dbRepository.getArticleById(id).collect {
                    _article.value = it
                }
            } catch (e: Exception) {
                e.message?.let { Log.e(TAG, it) }
            }
        }
    }
}
