package com.example.androidtest

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.androidtest.data.db.entity.ArticleEntity
import com.example.androidtest.ui.theme.AndroidTestTheme
import com.example.androidtest.viewmodel.NewsFeedViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: NewsFeedViewModel by viewModels()

    @OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            AndroidTestTheme {
                Scaffold(
                    modifier = Modifier
                        .fillMaxSize(),
                    topBar = {
                        TopAppBar(
                            title = { Text(text = getString(R.string.app_name)) },
                            colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.secondaryContainer)
                        )
                    }
                ) { contentPadding ->

                    val scope = rememberCoroutineScope()
                    val refreshing = viewModel.refreshing.collectAsState()
                    val pullRefreshState =
                        rememberPullRefreshState(
                            refreshing.value,
                            { scope.launch { viewModel.refresh() } })
                    val newsFeed = viewModel.topHeadlines.collectAsState()

                    Box(
                        modifier = Modifier
                            .padding(contentPadding)
                            .pullRefresh(state = pullRefreshState)
                    ) {
                        if (newsFeed.value.isNotEmpty()) {
                            FeedListView(newsFeed = newsFeed.value)
                        }

                        PullRefreshIndicator(
                            refreshing.value,
                            pullRefreshState,
                            Modifier.align(Alignment.TopCenter)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun FeedListView(newsFeed: List<ArticleEntity>, modifier: Modifier = Modifier) {
    val state = rememberLazyListState()

    LazyColumn(
        modifier = modifier,
        state = state,
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        items(newsFeed) {
            ArticleCardView(article = it)
        }
    }
}


@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun ArticleCardView(article: ArticleEntity) {
    ElevatedCard(
        modifier = Modifier.padding(8.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        )
    ) {
        Column(
            Modifier.padding(8.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = article.title,
                fontSize = 20.sp,
                textAlign = TextAlign.Start
            )

            GlideImage(
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth()
                    .height(150.dp),
                model = article.urlToImage,
                contentDescription = "",
                alignment = Alignment.Center,
                contentScale = ContentScale.FillWidth
            )

            Text(
                modifier = Modifier.padding(bottom = 8.dp),
                text = article.description,
                textAlign = TextAlign.Justify
            )

            Text(
                text = article.author,
                fontSize = 12.sp,
                textDecoration = TextDecoration.Underline,
                textAlign = TextAlign.Start
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ArticleCardViewPreview() {
    AndroidTestTheme {
        ArticleCardView(
            ArticleEntity(
                author = "toto",
                description = "Hello world",
                title = "News title",
                urlToImage = "https://image.cnbcfm.com/api/v1/image/106516157-1588351785968gettyimages-1209050413.jpeg?v=1695040122&w=1920&h=1080",
                content = "",
                publishedAt = "",
                url = "",
                source = null
            )
        )
    }
}