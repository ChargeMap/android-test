package com.example.androidtest.ui.composables.feed

import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.AppBarDefaults
import androidx.compose.material.ContentAlpha
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.IconButton
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.ElevatedFilterChip
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.androidtest.Constants
import com.example.androidtest.R
import com.example.androidtest.SettingsActivity
import com.example.androidtest.viewmodel.feed.NewsFeedViewModel
import com.example.androidtest.viewmodel.feed.SearchBarViewModel
import com.example.androidtest.viewmodel.feed.SearchWidgetState


@Composable
fun FeedAppBar(
    feedViewModel: NewsFeedViewModel = hiltViewModel(),
    topBarViewModel: SearchBarViewModel = hiltViewModel()
) {
    val searchWidgetState by topBarViewModel.searchWidgetState
    val inputText by topBarViewModel.inputText
    val searchHistory by topBarViewModel.searchHistory.collectAsState()

    MainAppBar(
        feedViewModel = feedViewModel,
        searchWidgetState = searchWidgetState,
        searchHistory = searchHistory,
        inputText = inputText,
        onTextChange = { newText -> topBarViewModel.onTextChanged(newText) },
        onCloseClicked = { topBarViewModel.updateWidgetState(SearchWidgetState.CLOSED) },
        onSearchClicked = { searchText ->
            if (searchText.isNotEmpty()) {
                feedViewModel.searchByText(searchText)
                topBarViewModel.addSearchToHistory(searchText)
                topBarViewModel.onTextChanged("")
                topBarViewModel.updateWidgetState(SearchWidgetState.CLOSED)
            }
        },
        onSearchTriggered = { topBarViewModel.updateWidgetState(SearchWidgetState.OPEN) }
    )
}

@Composable
fun MainAppBar(
    feedViewModel: NewsFeedViewModel,
    searchWidgetState: SearchWidgetState,
    inputText: String,
    searchHistory: List<String>,
    onTextChange: (String) -> Unit,
    onCloseClicked: () -> Unit,
    onSearchClicked: (String) -> Unit,
    onSearchTriggered: () -> Unit
) {
    when (searchWidgetState) {
        SearchWidgetState.OPEN -> {
            SearchAppBar(
                text = inputText,
                searchHistory = searchHistory,
                onTextChange = onTextChange,
                onCloseClicked = onCloseClicked,
                onSearchClicked = onSearchClicked
            )
        }

        SearchWidgetState.CLOSED -> {
            DefaultAppBar(feedViewModel = feedViewModel, onSearchTriggered = onSearchTriggered)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DefaultAppBar(
    feedViewModel: NewsFeedViewModel,
    onSearchTriggered: () -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    val showFilters by feedViewModel.showFilters.collectAsState()
    val context = LocalContext.current

    TopAppBar(
        title = { Text(text = stringResource(R.string.app_name)) },
        colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.secondaryContainer),
        actions = {

            IconButton(onClick = { onSearchTriggered() }) {
                Icon(imageVector = Icons.Default.Search, contentDescription = "Search Icon")
            }

            IconButton(onClick = { expanded = !expanded }) {
                Icon(
                    imageVector = Icons.Default.MoreVert,
                    contentDescription = "More"
                )
            }

            DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                DropdownMenuItem(onClick = {
                    feedViewModel.showHideFilters()
                    expanded = false
                }) {
                    Text(
                        text = stringResource(
                            id =
                            if (!showFilters) {
                                R.string.options_show_filter
                            } else {
                                R.string.options_hide_filter
                            }
                        )
                    )
                }

                DropdownMenuItem(onClick = {
                    context.startActivity(Intent(context, SettingsActivity::class.java))

                    expanded = false
                }) {
                    Text(text = stringResource(id = R.string.options_access_settings))
                }
            }
        }
    )
}


@Composable
fun SearchAppBar(
    text: String,
    searchHistory: List<String>,
    onTextChange: (String) -> Unit,
    onCloseClicked: () -> Unit,
    onSearchClicked: (String) -> Unit
) {
    val maxChar = 50

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        elevation = AppBarDefaults.TopAppBarElevation,
        color = MaterialTheme.colorScheme.secondaryContainer
    ) {
        Column {
            TextField(
                modifier = Modifier.fillMaxWidth(),
                value = text,
                onValueChange = {
                    if (it.length <= maxChar) {
                        onTextChange(it)
                    }
                },
                placeholder = {
                    Text(
                        modifier = Modifier.alpha(ContentAlpha.medium),
                        text = "Search..."
                    )
                },
                textStyle = TextStyle(fontSize = MaterialTheme.typography.titleSmall.fontSize),
                singleLine = true,
                leadingIcon = {
                    Icon(
                        modifier = Modifier.alpha(ContentAlpha.medium),
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search Icon"
                    )
                },
                trailingIcon = {
                    IconButton(onClick = {
                        if (text.isNotEmpty()) {
                            onTextChange("")
                        } else {
                            onCloseClicked()
                        }
                    }) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Close Icon"
                        )
                    }
                },
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                keyboardActions = KeyboardActions(onSearch = { onSearchClicked(text) }),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent
                ),
                supportingText = {
                    Text(
                        text = "${text.length} / $maxChar",
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.End,
                    )
                }
            )

            searchHistory.forEach {
                Row(modifier = Modifier
                    .padding(14.dp)
                    .background(Color.Transparent)
                    .clickable {
                        onSearchClicked(it)
                    }) {
                    Icon(
                        modifier = Modifier.padding(end = 10.dp),
                        painter = painterResource(id = R.drawable.baseline_history_24),
                        contentDescription = "history Icon"
                    )
                    Text(text = it)
                }
            }
        }
    }
}

@ExperimentalMaterial3Api
@Composable
fun FiltersBar(viewModel: NewsFeedViewModel = hiltViewModel()) {
    val filters by viewModel.filters.collectAsState()
    val modifier =
        Modifier.padding(horizontal = dimensionResource(id = R.dimen.default_padding))
    val categories = stringArrayResource(id = R.array.categories)

    LazyRow(
        modifier = modifier
            .fillMaxWidth()
            .background(color = Color.Transparent)
            .wrapContentHeight(Alignment.CenterVertically),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        item {
            val usFilterSelected = filters.country.contains(Constants.COUNTRY_US)

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
            val frFilterSelected = filters.country.contains(Constants.COUNTRY_FR)

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


@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview
fun FiltersBarPreview() {
    FiltersBar()
}
/*
@Composable
@Preview
fun SearchAppBarPreview() {
    SearchAppBar(
        text = "Hello World",
        onTextChange = {},
        onCloseClicked = { },
        onSearchClicked = {})
}*/