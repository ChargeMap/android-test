package com.example.androidtest.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidtest.Constants
import com.example.androidtest.R
import com.example.androidtest.data.db.entity.ArticleEntity
import com.example.androidtest.di.DbRepository
import com.example.androidtest.di.NewsApiRepository
import com.example.androidtest.util.DispatcherProvider
import com.example.androidtest.util.NetworkUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class NewsFeedViewModel @Inject constructor(
    private val apiRepository: NewsApiRepository,
    private val dbRepository: DbRepository,
    private val dispatcherProvider: DispatcherProvider,
    @ApplicationContext private val context: Context
) :
    ViewModel() {
    private val TAG = NewsFeedViewModel::class.simpleName

    private val _filters = MutableStateFlow(NewsFilters())
    val filters: StateFlow<NewsFilters> = _filters

    private val _showFilters = MutableStateFlow(false)
    val showFilters: StateFlow<Boolean> = _showFilters

    private val _uiState = MutableStateFlow<UiState<List<ArticleEntity>>>(UiState.Loading(true))
    val uiState: StateFlow<UiState<List<ArticleEntity>>> = _uiState

    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, exc ->
        viewModelScope.launch {
            _uiState.emit(UiState.Error("$TAG -----> exception handler: $exc"))
        }
    }

    init {
        refresh(true)
    }

    private suspend fun fetchNews() = apiRepository.getMultipleTopHeadlines(_filters.value)

    private fun handleResult(articleList: List<ArticleEntity>, insertIntoDb: Boolean = false) {
        viewModelScope.launch(dispatcherProvider.io) {
            _uiState.emit(
                when {
                    articleList.isNotEmpty() && insertIntoDb -> {
                        dbRepository.clearTable()
                        dbRepository.insertArticles(articleList)
                        UiState.Success(articleList)
                    }

                    articleList.isNotEmpty() -> UiState.Success(articleList)
                    else -> UiState.Error(context.getString(R.string.empty_list_message))
                }
            )
        }
    }

    fun refresh(firstLoading: Boolean = false) {
        viewModelScope.launch(coroutineExceptionHandler) {
            _uiState.emit(UiState.Loading(firstLoading))

            if (NetworkUtil.hasInternetConnection(context)) {
                val articleList = fetchNews()

                handleResult(articleList, true)
            } else {
                dbRepository.getAllArticlesByCountry(_filters.value.country)
                    .flowOn(dispatcherProvider.io).collect {
                        handleResult(it)
                    }
            }
        }
    }

    fun updateCountry(country: String) {
        viewModelScope.launch(coroutineExceptionHandler) {
            _filters.emit(_filters.value.copy(country = country))
        }
        refresh()
    }

    fun updateCategory(category: String) {
        val categories = if (_filters.value.categories.contains(category)) {
            _filters.value.categories.filterNot { it == category }
        } else {
            val newCategoriesList = _filters.value.categories.toMutableList()
            newCategoriesList.add(category)
            newCategoriesList
        }

        viewModelScope.launch(coroutineExceptionHandler) {
            _filters.emit(_filters.value.copy(categories = categories))
        }
        refresh()
    }

    fun showHideFilters() = viewModelScope.launch(dispatcherProvider.default) {
        _showFilters.emit(!_showFilters.value)
    }

    data class NewsFilters(
        val country: String = Constants.COUNTRY_US,
        val categories: List<String> = listOf(Constants.CATEGORY)
    )
}
