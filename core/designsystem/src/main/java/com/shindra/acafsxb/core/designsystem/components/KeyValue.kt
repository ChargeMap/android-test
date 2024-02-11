package com.shindra.acafsxb.core.designsystem.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
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
fun KeyValue(
    key: String,
    value: String,
    modifier: Modifier = Modifier,
    keyTextConfig: TextConfig = KeyValueTextConfig.defaultText(),
    valueTextConfig: TextConfig = KeyValueTextConfig.defaultText()
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = key,
            color = keyTextConfig.color,
            fontSize = keyTextConfig.fontSize,
            fontWeight = keyTextConfig.fontWeight,
            fontFamily = keyTextConfig.fontFamily,
            modifier = Modifier.padding(end = 4.dp)
        )
        Text(
            text = value,
            color = valueTextConfig.color,
            fontSize = valueTextConfig.fontSize,
            fontWeight = valueTextConfig.fontWeight,
            fontFamily = valueTextConfig.fontFamily,
            modifier = Modifier.padding(start = 4.dp)
        )
    }
}


@Preview(showBackground = true)
@Composable
fun KeyValuePreview() {
    KeyValue(
        key = "Clef",
        value = "Valeur"
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

@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL,
    showBackground = true
)
@Composable
fun KeyValueDarkPreview() {
    Column {
        KeyValue(
            key = "Clef",
            value = "Valeur"
        )

        Card {
            KeyValue(
                key = "Clef",
                value = "Valeur"
            )
        }

    }


}