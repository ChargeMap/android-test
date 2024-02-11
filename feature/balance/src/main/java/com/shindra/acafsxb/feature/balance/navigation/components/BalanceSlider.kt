package com.shindra.acafsxb.feature.balance.navigation.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import    androidx.compose.material3.Icon
import androidx.compose.material3.Slider
import androidx.compose.material3.Text

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.shindra.acafsxb.core.designsystem.theme.AcafSxbTheme
import kotlin.math.absoluteValue
import kotlin.math.roundToInt


@Composable
internal fun BalanceSlider(
    icon: ImageVector,
    sliderValue: Float,
    onSliderValueChange: (Float) -> Unit,
    onValueChangeFinished: () -> Unit = {},
    unit: String,
    name: String,
    valueRange: ClosedFloatingPointRange<Float>,
    modifier: Modifier = Modifier
    /*
     isInError : Boolean*/
) {
    Column(modifier = modifier) {
        Text(name)
        BalanceSlider(
            sliderValue = sliderValue,
            onSliderValueChange = onSliderValueChange,
            unit = unit,
            iv = icon,
            valueRange = valueRange,
            onValueChangeFinished = onValueChangeFinished
        )
    }
}

@Composable
private fun BalanceSlider(
    iv: ImageVector,
    sliderValue: Float,
    onSliderValueChange: (Float) -> Unit,
    valueRange: ClosedFloatingPointRange<Float>,
    onValueChangeFinished: () -> Unit,
    unit: String
) {

    Row(verticalAlignment = Alignment.CenterVertically) {

        Icon(
            imageVector = iv,
            contentDescription = null
        )

        Slider(
            value = sliderValue,
            onValueChange = onSliderValueChange,
            valueRange = valueRange,
            onValueChangeFinished = onValueChangeFinished,
            modifier = Modifier
                .weight(1f)
                .padding(
                    start = 8.dp,
                    end = 4.dp
                )
        )
        Text(
            text = sliderValue.roundToInt().toString(),
            modifier = Modifier
                .width(35.dp)
                .padding(end = 4.dp)
        )
        Text(text = unit)
    }
}

@Composable
@Preview(showBackground = true, showSystemUi = false)
private fun BalanceSliderPreview() {

    var state by remember {
        mutableFloatStateOf(0f)
    }

    AcafSxbTheme {
        BalanceSlider(
            icon = Icons.Outlined.ArrowBack,
            sliderValue = state,
            onSliderValueChange = { state = it },
            name = "Command de bord",
            unit = "Kg",
            valueRange = 16f..204f
        )
    }
}


@Composable
@Preview(
    showBackground = true,
    showSystemUi = false,
    uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL
)
private fun BalanceSliderPreviewDark() {

    var state by remember {
        mutableFloatStateOf(100f)
    }

    AcafSxbTheme {
        BalanceSlider(
            icon = Icons.Outlined.ArrowBack,
            sliderValue = state,
            onSliderValueChange = { state = it },
            name = "Command de bord",
            unit = "Kg",
            valueRange = 16f..204f
        )
    }
}