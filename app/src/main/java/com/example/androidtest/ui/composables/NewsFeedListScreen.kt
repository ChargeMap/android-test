package com.example.androidtest.ui.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.androidtest.data.db.entity.ArticleEntity
import com.example.androidtest.viewmodel.NewsFeedViewModel
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun NewsFeedListScreen(
    modifier: Modifier,
    navController: NavController,
    viewModel: NewsFeedViewModel,
) {
    val scope = rememberCoroutineScope()
    val refreshing = viewModel.refreshing.collectAsState()
    val pullRefreshState =
        rememberPullRefreshState(
            refreshing.value,
            { scope.launch { viewModel.refresh() } })
    val newsFeed = viewModel.topHeadlines.collectAsState()

    Box(
        modifier = modifier.pullRefresh(state = pullRefreshState)
    ) {
        if (newsFeed.value.isNotEmpty()) {
            FeedListView(
                navController = navController,
                newsFeed = newsFeed.value
            )
        }

        PullRefreshIndicator(
            refreshing.value,
            pullRefreshState,
            Modifier.align(Alignment.TopCenter)
        )
    }
}

@Composable
fun FeedListView(
    navController: NavController,
    newsFeed: List<ArticleEntity>,
) {
    val state = rememberLazyListState()

    LazyColumn(
        modifier = Modifier.wrapContentSize(),
        state = state,
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        items(newsFeed) {
            ArticleCardView(navController = navController, article = it)
        }
    }
}