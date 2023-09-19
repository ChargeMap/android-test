package com.example.androidtest.views

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import com.example.androidtest.data.db.entity.ArticleEntity
import kotlinx.coroutines.flow.StateFlow

@Composable
fun ArticleDetailScreen(modifier: Modifier = Modifier, articleFlow: StateFlow<ArticleEntity?>) {
    val article = articleFlow.collectAsState()

    Box(modifier = modifier.wrapContentHeight()) {
        article.value?.let {
            ArticleCardView(article = it, isDetails = true)
        }
    }
}