@file:OptIn(ExperimentalFoundationApi::class)

package com.shindra.acafsxb.feature.weather.map

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.shindra.acafsxb.core.designsystem.components.EmbeddedTabLayout
import com.shindra.acafsxb.core.designsystem.components.EmbeddedTabLayoutDefaults
import com.shindra.acafsxb.core.designsystem.components.PdfReader
import com.shindra.acafsxb.core.designsystem.components.UiStateContent
import com.shindra.acafsxb.core.model.MapAltitude
import com.shindra.acafsxb.core.model.MapType
import com.shindra.acafsxb.core.model.MapZone
import com.shindra.acafsxb.feature.weather.R
import com.shindra.acafsxb.core.designsystem.theme.AcafSxbTheme
import kotlinx.coroutines.launch

/**
 * Todo : Weather map like TEMSI can be amended. Currently the cache system with the maturity date will have no way to invalidate an amended map on the server
 *   The data object returned by the api has no information if the map has been amended.
 *   The best option is to change the cache system and rely on the login token to invalidate the cache.
 *   This data change every 5 minutes. We will do more request to the weather api, but we will be granted to have the latest map.
 *   We should also display in the toolbar à update button to force a network call
 */
@Composable
internal fun WeatherWeatherMapScreenRoute(
    onTitleChange: (String) -> Unit,
    mapType: MapType,
    mapZone: MapZone,
    availableAltitude: List<MapAltitude>,
    viewModel: WeatherMapViewModel = hiltViewModel()
) {

    val title = stringResource(
        when (mapType) {
            MapType.Temsi -> R.string.weather_temsi_toolbar_title
            MapType.Wintemp -> R.string.weather_wintem_toolbar_title
        }
    )

    LaunchedEffect(key1 = title) {
        onTitleChange(title)
        viewModel.getMapsByMaturity(
            zone = mapZone.key,
            type = mapType,
            mapAltitude = MapAltitude.Fl050
        )
    }

    WeatherMapScreen(viewModel.mapUiState.collectAsStateWithLifecycle().value)
}


@Composable
internal fun WeatherMapScreen(mapState: WeatherMapUiState) {

    var currentIndex by remember {
        mutableStateOf(0)
    }

  /*  val pagerState = rememberPagerState()*/
    val coroutineScope = rememberCoroutineScope()


    UiStateContent(state = mapState) { maps ->
        Column() {

           /* WeatherEmbeddedTabLayout(
                selectedIndex = pagerState.currentPage,
                onIndexChange = {
                    coroutineScope.launch {
                        pagerState.animateScrollToPage(it)
                    }
                },
                utcTimes = maps.map { map -> map.utcTime },
                modifier = Modifier.padding(
                    horizontal = 16.dp,
                    vertical = 16.dp
                )
            )
*/
           /* HorizontalPager(
                pageCount = maps.size,
                state = pagerState,
                modifier = Modifier.fillMaxSize()
            ) {
                *//* val color = if(pagerState.currentPage % 2 ==0) Color.Blue else Color.Red
                 Box(modifier = Modifier.background(color))*//*

                PdfReader(
                    pdfUri = maps[it].uri
                )
            }*/

            //PdfReader(it.first().uri)
        }
    }
}

@Composable
private fun WeatherEmbeddedTabLayout(
    selectedIndex: Int,
    onIndexChange: (Int) -> Unit,
    utcTimes: List<String>,
    modifier: Modifier = Modifier
) {
    EmbeddedTabLayout(
        selectedIndex = selectedIndex,
        onIndexChange = onIndexChange,
        nbOfTabs = utcTimes.size,
        colors = EmbeddedTabLayoutDefaults.colors(
            backgroundColor = Color.White
        ),
        modifier = modifier
    ) { tabIndex ->

        val selected = tabIndex == selectedIndex

        Text(
            text = utcTimes[tabIndex],
            color = if (selected) Color.White else Color.Unspecified,
            modifier = Modifier.padding(4.dp)
        )
    }
}

@Composable
@Preview
fun WeatherEmbeddedTabLayoutPreview() {
    var currentIndex by remember {
        mutableStateOf(0)
    }
    AcafSxbTheme() {
        WeatherEmbeddedTabLayout(
            selectedIndex = currentIndex,
            onIndexChange = { index -> currentIndex = index },
            utcTimes = listOf(
                "15UTC",
                "17UTC",
                "21UTC",
                "00UTC"
            )
        )
    }
}