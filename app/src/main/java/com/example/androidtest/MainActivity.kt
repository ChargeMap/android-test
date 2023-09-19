package com.example.androidtest

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Switch
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.androidtest.ui.composables.ArticleDetailScreen
import com.example.androidtest.ui.composables.NewsFeedListScreen
import com.example.androidtest.ui.theme.AndroidTestTheme
import com.example.androidtest.viewmodel.NewsFeedViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: NewsFeedViewModel by viewModels()

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val checkedState = remember { mutableStateOf(false) }

            AndroidTestTheme(darkTheme = checkedState.value) {
                Scaffold(
                    modifier = Modifier
                        .fillMaxSize(),
                    topBar = {
                        TopAppBar(
                            title = { Text(text = getString(R.string.app_name)) },
                            colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.secondaryContainer),
                            actions = {
                                Text(
                                    modifier = Modifier.padding(end = dimensionResource(id = R.dimen.default_padding)),
                                    text = stringResource(id = R.string.switch_ui_mode)
                                )
                                Switch(
                                    checked = checkedState.value,
                                    enabled = true,
                                    onCheckedChange = {
                                        checkedState.value = it
                                    })

                            }
                        )
                    }
                ) { contentPadding ->
                    val navController = rememberNavController()
                    NavigationComponent(
                        modifier = Modifier.padding(contentPadding),
                        navController = navController,
                        viewModel = viewModel,
                    )
                }
            }
        }
    }
}

@Composable
fun NavigationComponent(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    viewModel: NewsFeedViewModel
) {
    NavHost(
        navController = navController,
        startDestination = "feedList"
    ) {
        composable("feedList") {
            NewsFeedListScreen(
                modifier = modifier,
                navController = navController,
                viewModel = viewModel
            )
        }
        composable(
            "details/{articleId}",
            arguments = listOf(navArgument("articleId") { type = NavType.IntType })
        ) {
            val articleId = it.arguments?.getInt("articleId")
            val scope = rememberCoroutineScope()

            articleId?.let {
                scope.launch {
                    viewModel.getArticleById(articleId)
                }
                ArticleDetailScreen(modifier = modifier, articleFlow = viewModel.article)
            }
        }
    }
}