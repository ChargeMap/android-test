package com.shindra.acafsxb.feature.planes

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

@Composable
internal fun PlanesScreenRoute(
    onTitleChange: (String) -> Unit,
    onMassAndBalanceClick: (String) -> Unit,
    viewModel: PlanesViewModel = hiltViewModel()
) {

    val planesUiState by viewModel.planeUiState.collectAsStateWithLifecycle()

    val title = stringResource(id = R.string.plane_toolbar_title)
    LaunchedEffect(key1 = title) {
        onTitleChange(title)
    }

    PlanesScreen(
        planes = planesUiState,
        onMassAndBalanceClick = onMassAndBalanceClick
    )
}

@Composable
internal fun PlanesScreen(
    planes: PlaneInfoState,
    onMassAndBalanceClick: (String) -> Unit
) {
    UiStateContent(state = planes) {
        LazyColumn(
            contentPadding = PaddingValues(horizontal = 8.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(it) {
                PlaneCard(
                    registration = it.manufacturer,
                    hourlyCost = "",
                    imageUrl = "it.imageUrl",
                    manufacturerAndType = "it.modelAndManufacturer",
                    onClick = onMassAndBalanceClick,
                    mtow = "it.mtow",
                    nbOfSeats = 4,
                    minOilQuantityBadge = "it.minOilQuantity"
                )
            }
        }
    }
}