package com.shindra.acafsxb.feature.planedetails

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.shindra.acafsxb.core.designsystem.components.KeyValueTextConfig
import com.shindra.acafsxb.core.designsystem.components.KeyValueUnit
import com.shindra.acafsxb.core.designsystem.components.UiStateContent

@Composable
internal fun PlaneDetailScreenRoute(
    onTitleChange: (String) -> Unit,
    manufacturer: String,
    model: String,
    viewModel: PlaneDetailsViewModel = hiltViewModel()
) {

    val planesUiState by viewModel.planeDetailsState.collectAsStateWithLifecycle()

    LaunchedEffect(key1 = "$manufacturer$model") {
        onTitleChange("$manufacturer $model")
    }

    PlaneDetailScreen(planesUiState)
}


@Composable
private fun PlaneDetailScreen(
    planeDetail: PlaneDetailUiState
) {
    UiStateContent(state = planeDetail) {
        Column(modifier = Modifier.padding(horizontal = 8.dp)) {
            it.forEach { d ->
                KeyValueUnit(
                    key = stringResource(id = d.key),
                    value = d.value,
                    unit = d.unit?.let { stringResource(it) },
                    keyTextConfig = KeyValueTextConfig.defaultText(fontWeight = FontWeight.ExtraBold),
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }
        }
    }
}