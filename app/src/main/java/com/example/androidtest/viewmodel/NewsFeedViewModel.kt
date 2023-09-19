package com.example.androidtest.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidtest.data.db.entity.ArticleEntity
import com.example.androidtest.di.DbRepository
import com.example.androidtest.di.NewsApiRepository
import com.example.androidtest.models.Article
import dagger.hilt.android.lifecycle.HiltViewModel
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

    private val _topHeadlines = MutableStateFlow<List<ArticleEntity>>(emptyList())
    val topHeadlines: StateFlow<List<ArticleEntity>> = _topHeadlines

    init {
        viewModelScope.launch {
            apiRepository.getTopHeadlines()
            dbRepository.getAllArticles().collect {
                _topHeadlines.value = it
            }
        }
    }
}
