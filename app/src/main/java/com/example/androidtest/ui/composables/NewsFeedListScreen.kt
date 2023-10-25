package com.example.androidtest.ui.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.ElevatedFilterChip
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Alignment.Companion.TopCenter
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.example.androidtest.Constants
import com.example.androidtest.R
import com.example.androidtest.data.db.entity.ArticleEntity
import com.example.androidtest.viewmodel.NewsFeedViewModel
import com.example.androidtest.viewmodel.UiState
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OptionsMenu(checkedState: MutableState<Boolean>, viewModel: NewsFeedViewModel) {

    var expanded by remember { mutableStateOf(false) }
    val displayFilters by viewModel.showFilters.collectAsState()

    TopAppBar(
        title = { Text(text = stringResource(R.string.app_name)) },
        colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.secondaryContainer),
        actions = {
            IconButton(onClick = { expanded = !expanded }) {
                Icon(imageVector = Icons.Default.MoreVert, contentDescription = "")
            }

            DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                DropdownMenuItem(onClick = {
                    checkedState.value = !checkedState.value
                    expanded = false
                }) {
                    Text(
                        text = stringResource(
                            id = if (!checkedState.value) {
                                R.string.options_enable_dark_mode
                            } else {
                                R.string.options_disable_dark_mode
                            }
                        )
                    )
                }

                DropdownMenuItem(onClick = {
                    viewModel.showHideFilters()
                    expanded = false
                }) {
                    Text(
                        text = stringResource(
                            id =
                            if (!displayFilters) {
                                R.string.options_show_filter
                            } else {
                                R.string.options_hide_filter
                            }
                        )
                    )
                }
            }
        }
    )
}

@ExperimentalMaterial3Api
@Composable
fun FiltersBar(viewModel: NewsFeedViewModel) {
    val filters by viewModel.filters.collectAsState()
    val modifier = Modifier.padding(horizontal = dimensionResource(id = R.dimen.default_padding))
    val categories = stringArrayResource(id = R.array.categories)

    LazyRow(
        modifier = modifier
            .fillMaxWidth()
            .background(color = Color.Transparent)
            .wrapContentHeight(CenterVertically),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        item {
            val usFilterSelected = filters.country == Constants.COUNTRY_US

            ElevatedFilterChip(
                modifier = modifier,
                onClick = {
                    viewModel.updateCountry(Constants.COUNTRY_US)
                },
                label = {
                    Text(stringResource(id = R.string.filter_us_feed))
                },
                selected = usFilterSelected,
                leadingIcon = if (usFilterSelected) {
                    {
                        Icon(
                            imageVector = Icons.Filled.Done,
                            contentDescription = "Done icon",
                            modifier = Modifier.size(FilterChipDefaults.IconSize)
                        )
                    }
                } else {
                    null
                },
            )
        }

        item {
            val frFilterSelected = filters.country == Constants.COUNTRY_FR

            ElevatedFilterChip(
                modifier = modifier,
                onClick = {
                    viewModel.updateCountry(Constants.COUNTRY_FR)
                },
                label = {
                    Text(stringResource(id = R.string.filter_french_feed))
                },
                selected = frFilterSelected,
                leadingIcon = if (frFilterSelected) {
                    {
                        Icon(
                            imageVector = Icons.Filled.Done,
                            contentDescription = "Done icon",
                            modifier = Modifier.size(FilterChipDefaults.IconSize)
                        )
                    }
                } else {
                    null
                },
            )
        }

        items(categories) {
            val isSelected = filters.categories.contains(it)

            ElevatedFilterChip(
                modifier = modifier,
                onClick = {
                    viewModel.updateCategory(it)
                },
                label = {
                    Text(it)
                },
                selected = isSelected,
                leadingIcon = if (isSelected) {
                    {
                        Icon(
                            imageVector = Icons.Filled.Done,
                            contentDescription = "Done icon",
                            modifier = Modifier.size(FilterChipDefaults.IconSize)
                        )
                    }
                } else {
                    null
                },
            )
        }
    }
}

@OptIn(ExperimentalMaterialApi::class, ExperimentalMaterial3Api::class)
@Composable
fun NewsFeedListScreen(
    modifier: Modifier,
    navController: NavController,
    viewModel: NewsFeedViewModel,
    snackbarHostState: SnackbarHostState
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
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (showFilters) {
                FiltersBar(viewModel = viewModel)
            }

            when (uiState) {
                is UiState.Success -> FeedListView(
                    navController = navController,
                    newsFeed = (uiState as UiState.Success<List<ArticleEntity>>).data
                )

                is UiState.Error -> {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(rememberScrollState())
                    ) {
                        LaunchedEffect(true) {
                            scope.launch {
                                snackbarHostState.showSnackbar(
                                    (uiState as UiState.Error).error,
                                    duration = SnackbarDuration.Short
                                )
                            }
                        }
                    }
                }

                is UiState.Loading -> {
                    refreshing = !(uiState as UiState.Loading).firstLoading
                }
            }
        }

        if (refreshing) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
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
    newsFeed: List<ArticleEntity>
) {
    val state = rememberLazyListState()

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        state = state,
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        items(newsFeed) {
            ArticleCardView(navController = navController, article = it)
        }
    }
}