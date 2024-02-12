package com.shindra.chargemap.core.designsystem.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp

@Composable
fun KeyValueUnit(
    key: String,
    value: String,
    unit: String?,
    modifier: Modifier = Modifier,
    keyTextConfig: TextConfig = KeyValueTextConfig.defaultText(),
    valueTextConfig: TextConfig = KeyValueTextConfig.defaultText(),
    unitTextConfig: TextConfig = KeyValueTextConfig.defaultText()
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        with(keyTextConfig) {
            Text(
                text = key,
                color = color,
                fontSize = fontSize,
                fontWeight = fontWeight,
                fontFamily = fontFamily,
                modifier = Modifier.padding(end = 4.dp)
            )
        }

        Row {
            with(valueTextConfig) {
                Text(
                    text = value,
                    color = color,
                    fontSize = fontSize,
                    fontWeight = fontWeight,
                    fontFamily = fontFamily,
                    modifier = Modifier.padding(end = 4.dp)
                )
            }

            if (unit == null) return

            with(unitTextConfig) {
                Text(
                    text = unit,
                    color = color,
                    fontSize = fontSize,
                    fontWeight = fontWeight,
                    fontFamily = fontFamily,
                    modifier = Modifier.padding(end = 4.dp)
                )
            }
        }

    }
}


@Preview(showBackground = true)
@Composable
fun KeyValueLightPreview() {
    KeyValueUnit(
        key = "Clef",
        value = "Valeur",
        "Unit"
    )
}


@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun KeyValueDarkPreview() {
    KeyValueUnit(
        key = "Clef",
        value = "Valeur",
        "Unit"
    )
}

object KeyValueTextConfig {
    fun defaultText(
        color: Color = Color.Unspecified,
        fontSize: TextUnit = TextUnit.Unspecified,
        fontStyle: FontStyle? = null,
        fontWeight: FontWeight? = null,
        fontFamily: FontFamily? = null
    ) = TextConfig(
        color = color,
        fontSize = fontSize,
        fontStyle = fontStyle,
        fontWeight = fontWeight,
        fontFamily = fontFamily
    )
}

data class TextConfig internal constructor(
    val color: Color,
    val fontSize: TextUnit,
    val fontStyle: FontStyle?,
    val fontWeight: FontWeight?,
    val fontFamily: FontFamily?,
)