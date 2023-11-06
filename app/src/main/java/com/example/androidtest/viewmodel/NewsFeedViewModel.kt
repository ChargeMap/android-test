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

    companion object {
        enum class FilterType {
            COUNTRY,
            CATEGORY
        }
    }

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
        updateFilter(country, FilterType.COUNTRY)
    }

    fun updateCategory(category: String) {
        updateFilter(category, FilterType.CATEGORY)
    }

    private fun updateFilter(filter: String, filterType: FilterType) {
        val filterList = when (filterType) {
            FilterType.COUNTRY -> _filters.value.country
            FilterType.CATEGORY -> _filters.value.categories
        }


        val newFilters = if (filterList.contains(filter)) {
            filterList.filterNot { it == filter }
        } else {
            val newCategoriesList = filterList.toMutableList()
            newCategoriesList.add(filter)
            newCategoriesList
        }

        viewModelScope.launch(coroutineExceptionHandler) {
            _filters.emit(
                if (filterType == FilterType.CATEGORY) {
                    _filters.value.copy(categories = newFilters)
                } else {
                    _filters.value.copy(country = newFilters)
                }
            )
        }
        refresh()
    }

    fun showHideFilters() = viewModelScope.launch(dispatcherProvider.default) {
        _showFilters.emit(!_showFilters.value)
    }

    data class NewsFilters(
        val country: List<String> = listOf(Constants.COUNTRY_US),
        val categories: List<String> = listOf(Constants.CATEGORY)
    )
}
