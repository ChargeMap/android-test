package com.example.androidtest.ui.composables

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.androidtest.data.db.entity.ArticleEntity
import com.example.androidtest.viewmodel.ArticleDetailViewModel
import com.example.androidtest.viewmodel.UiState

@Composable
fun ArticleDetailScreen(
    modifier: Modifier = Modifier,
    articleId: Int,
    viewModel: ArticleDetailViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    viewModel.fetchArticle(articleId)

    ArticleDetailScreen(modifier = modifier, uiState = uiState)
}


@Composable
fun ArticleDetailScreen(modifier: Modifier = Modifier, uiState: UiState<ArticleEntity>?) {
    Box(modifier = modifier.fillMaxSize()) {
        if (uiState is UiState.Success) {
            ArticleCardView(
                article = uiState.data,
                isDetails = true
            )
        }
    }
}