package com.shindra.acafsxb.feature.weather.menu

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role.Companion.Image
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.shindra.acafsxb.feature.weather.R
import com.shindra.acafsxb.feature.weather.bo.WeatherMenuEntryUi
import com.shindra.acafsxb.feature.weather.bo.WeatherMenuType

internal typealias OnWeatherEntryClick = (WeatherMenuType) -> Unit

@Composable
internal fun WeatherMenuScreenRoute(
    onTitleChange: (String) -> Unit,
    onEntryClick: OnWeatherEntryClick,
    viewModel: WeatherMenuViewModel = hiltViewModel()
) {
    val weatherMenuState by viewModel.menuState.collectAsStateWithLifecycle()

    onTitleChange(stringResource(id = R.string.weather_menu_toolbar_title))

    WeatherMenuScreen(
        onEntryClick = onEntryClick,
        state = weatherMenuState
    )

}

@Composable
internal fun WeatherMenuScreen(
    state: List<WeatherMenuEntryUi>,
    onEntryClick: OnWeatherEntryClick
) {

    LazyVerticalGrid(
        columns = GridCells.Adaptive(160.dp),
        contentPadding = PaddingValues(8.dp),
        modifier = Modifier.fillMaxSize()
    ) {
        itemsIndexed(state) { _, item ->
            WeatherMenuEntry(
                title = item.title,
                drawableId = item.icon,
                onClick = { onEntryClick(item.entry) }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun WeatherMenuEntry(
    @StringRes title: Int,
    @DrawableRes drawableId: Int,
    onClick: () -> Unit
) {

    Card(
        modifier = Modifier
            .padding(4.dp)
            .height(150.dp),
        onClick = onClick
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally

        ) {
            Image(
                painter = painterResource(id = drawableId),
                contentDescription = null
            )
            Text(
                text = stringResource(id = title),
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(24.dp)
            )
        }
    }

}

@Preview
@Composable
private fun WeatherMenuEntryPreview() {
    WeatherMenuEntry(
        title = R.string.weather_wintem_menu_title,
        drawableId = R.drawable.ic_wind,
        onClick = {}
    )
}