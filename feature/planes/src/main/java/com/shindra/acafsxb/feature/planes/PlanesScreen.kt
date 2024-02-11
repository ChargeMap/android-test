package com.shindra.acafsxb.feature.planes

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
import com.shindra.acafsxb.core.designsystem.components.UiStateContent
import com.shindra.acafsxb.feature.planes.compose.PlaneCard
import com.shindra.acafsxb.feature.planes.compose.PlaneHeader

@Composable
internal fun PlanesScreenRoute(
    onTitleChange: (String) -> Unit,
    onMassAndBalanceClick: (String) -> Unit,
    viewModel: PlanesViewModel = hiltViewModel()
) {

    val planesUiState by viewModel.planesByCategoryState.collectAsStateWithLifecycle()

    val title = stringResource(id = R.string.plane_toolbar_title)
    LaunchedEffect(key1 = title) {
        onTitleChange(title)
    }

    PlanesScreen(
        planesByCategory = planesUiState,
        onMassAndBalanceClick = onMassAndBalanceClick
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
internal fun PlanesScreen(
    planesByCategory: PlaneUi,
    onMassAndBalanceClick: (String) -> Unit
) {
    UiStateContent(state = planesByCategory) { pC ->
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
                        imageUrl = "",
                        onClick = {}
                    )
                }
            }
        }
    }
}
