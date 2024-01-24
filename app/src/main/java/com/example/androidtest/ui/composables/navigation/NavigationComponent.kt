package com.example.androidtest.ui.composables.navigation

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.androidtest.ui.composables.articles.ArticleDetailScreen
import com.example.androidtest.ui.composables.feed.NewsFeedListScreen
import com.example.androidtest.viewmodel.feed.NewsFeedViewModel

@Composable
fun NavigationComponent(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    viewModel: NewsFeedViewModel,
    snackbarHostState: SnackbarHostState
) {
    NavHost(
        navController = navController,
        startDestination = "feedList"
    ) {
        composable("feedList") {
            NewsFeedListScreen(
                modifier = modifier,
                navController = navController,
                viewModel = viewModel,
                snackBarHostState = snackbarHostState
            )
        }
        composable(
            "details/{articleId}",
            arguments = listOf(navArgument("articleId") { type = NavType.IntType })
        ) {
            val articleId = it.arguments?.getInt("articleId")

            articleId?.let {
                ArticleDetailScreen(modifier = modifier, articleId = articleId)
            }
        }
    }
}