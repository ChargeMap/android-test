package com.example.androidtest.ui.composables.feed

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.TopCenter
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.androidtest.data.db.entity.ArticleEntity
import com.example.androidtest.ui.composables.articles.ArticleCardView
import com.example.androidtest.ui.composables.navigation.NavigationComponent
import com.example.androidtest.viewmodel.UiState
import com.example.androidtest.viewmodel.feed.NewsFeedViewModel
import kotlinx.coroutines.launch

@Composable
fun MainScreen(viewModel: NewsFeedViewModel){
    val snackBarHostState = remember { SnackbarHostState() }

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        snackbarHost = { SnackbarHost(snackBarHostState) },
        topBar = {
            FeedAppBar()
        }
    ) { contentPadding ->
        val navController = rememberNavController()

        NavigationComponent(
            modifier = Modifier.padding(contentPadding),
            navController = navController,
            viewModel = viewModel,
            snackbarHostState = snackBarHostState
        )
    }
}


@OptIn(ExperimentalMaterialApi::class, ExperimentalMaterial3Api::class)
@Composable
fun NewsFeedListScreen(
    modifier: Modifier,
    navController: NavController,
    viewModel: NewsFeedViewModel = hiltViewModel(),
    snackBarHostState: SnackbarHostState
) {
    val scope = rememberCoroutineScope()
    var refreshing = remember { false }
    val pullRefreshState =
        rememberPullRefreshState(
            refreshing,
            { scope.launch { viewModel.refresh() } })

    val uiState by viewModel.uiState.collectAsState()
    val showFilters by viewModel.showFilters.collectAsState()

    Box(
        modifier = modifier
            .fillMaxSize()
            .pullRefresh(state = pullRefreshState)
    ) {
        when (uiState) {
            is UiState.Success -> FeedListView(
                navController = navController,
                newsFeed = (uiState as UiState.Success<List<ArticleEntity>>).data,
                showFilters = showFilters
            )

            is UiState.Error -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                ) {
                    LaunchedEffect(true) {
                        scope.launch {
                            snackBarHostState.showSnackbar(
                                (uiState as UiState.Error).error,
                                duration = SnackbarDuration.Short
                            )
                        }
                    }
                }
            }

            is UiState.Loading -> {
                refreshing = !(uiState as UiState.Loading).firstLoading
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
        }

        AnimatedVisibility(
            visible = showFilters,
            enter = fadeIn() + expandVertically(),
            exit = fadeOut() + shrinkVertically()
        ) {
            FiltersBar(viewModel = viewModel)
        }

        PullRefreshIndicator(
            refreshing,
            pullRefreshState,
            Modifier.align(TopCenter)
        )
    }
}

@Composable
fun FeedListView(
    navController: NavController,
    newsFeed: List<ArticleEntity>,
    showFilters: Boolean
) {
    val state = rememberLazyListState()
    val padding by animateDpAsState(targetValue = if (showFilters) 48.dp else 0.dp, label = "")

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        state = state,
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
        contentPadding = PaddingValues(top = padding)
    ) {
        items(newsFeed) {
            ArticleCardView(navController = navController, article = it)
        }
    }
}