package com.example.androidtest

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.preference.PreferenceManager
import com.example.androidtest.ui.composables.ArticleDetailScreen
import com.example.androidtest.ui.composables.NewsFeedListScreen
import com.example.androidtest.ui.composables.OptionsMenu
import com.example.androidtest.ui.theme.AndroidTestTheme
import com.example.androidtest.viewmodel.NewsFeedViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: NewsFeedViewModel by viewModels()
    private lateinit var darkThemeState: MutableState<Boolean>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            checkPreferences()
            val snackBarHostState = remember { SnackbarHostState() }

            AndroidTestTheme(darkTheme = darkThemeState.value) {
                Scaffold(
                    modifier = Modifier
                        .fillMaxSize(),
                    snackbarHost = { SnackbarHost(snackBarHostState) },
                    topBar = {
                        OptionsMenu()
                    }
                ) { contentPadding ->
                    val navController = rememberNavController()

                    NavigationComponent(
                        modifier = Modifier
                            .padding(contentPadding),
                        navController = navController,
                        viewModel = viewModel,
                        snackbarHostState = snackBarHostState
                    )
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()

        checkPreferences()
    }

    private fun checkPreferences() {
        val preferences = PreferenceManager.getDefaultSharedPreferences(this@MainActivity)
        darkThemeState =
            mutableStateOf(preferences.getBoolean(Constants.PREFERENCE_DARK_MODE, false))
    }
}


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
                snackbarHostState
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