package com.shindra.chargemap.feature.planes

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.shindra.chargemap.core.designsystem.components.FullScreenErrorScreen
import com.shindra.chargemap.core.designsystem.components.UiStateContent
import com.shindra.chargemap.feature.planes.compose.PlaneCard
import com.shindra.chargemap.feature.planes.compose.PlaneHeader

typealias OnPlaneClick = (manufacturer: String, model: String) -> Unit

@Composable
internal fun PlanesScreenRoute(
    onTitleChange: (String) -> Unit,
    onPlaneClick: OnPlaneClick,
    viewModel: PlanesViewModel = hiltViewModel()
) {

    val planesUiState by viewModel.planesByCategoryState.collectAsStateWithLifecycle()

    val title = stringResource(id = R.string.plane_toolbar_title)
    LaunchedEffect(key1 = title) {
        onTitleChange(title)
    }

    PlanesScreen(
        planesByCategory = planesUiState,
        onPlaneClick = onPlaneClick,
        onRetry = { viewModel.planes() }
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
internal fun PlanesScreen(
    planesByCategory: PlaneUi,
    onPlaneClick: OnPlaneClick,
    onRetry: () -> Unit
) {
    UiStateContent(
        onError = { FullScreenErrorScreen(onRetry) },
        state = planesByCategory
    ) { pC ->
        LazyColumn(
            contentPadding = PaddingValues(horizontal = 8.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            pC.forEach {
                stickyHeader {
                    PlaneHeader(it.title)
                }
                items(it.items) {
                    PlaneCard(
                        manufacturer = it.manufacturer,
                        model = it.model,
                        imageUrl = it.url,
                        onClick = {
                            onPlaneClick(it.manufacturer, it.model)
                        }
                    )
                }
            }
        }
    }
}
