package com.example.androidtest.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidtest.data.db.entity.ArticleEntity
import com.example.androidtest.di.DbRepository
import com.example.androidtest.util.DispatcherProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ArticleDetailViewModel @Inject constructor(
    private val dbRepository: DbRepository,
    private val dispatcherProvider: DispatcherProvider
) :
    ViewModel() {

    private val TAG = ArticleDetailViewModel::class.java.simpleName


    private val _uiState = MutableStateFlow<UiState<ArticleEntity>>(UiState.Loading(false))
    val uiState: StateFlow<UiState<ArticleEntity>> = _uiState

    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        viewModelScope.launch {
            _uiState.emit(UiState.Error("$TAG -----> exception handler: $throwable"))
        }
    }

    fun fetchArticle(articleId: Int) {
        viewModelScope.launch(coroutineExceptionHandler) {
            _uiState.emit(UiState.Loading())
            dbRepository.getArticleById(articleId).flowOn(dispatcherProvider.io).collect {
                _uiState.emit(UiState.Success(it))
            }
        }
    }

}