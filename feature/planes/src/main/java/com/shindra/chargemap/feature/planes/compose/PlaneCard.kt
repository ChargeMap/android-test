package com.shindra.chargemap.feature.planes.compose

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.shindra.chargemap.core.designsystem.components.CoilNetworkImage
import com.shindra.chargemap.core.designsystem.theme.ChargeMapTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun PlaneCard(
    manufacturer: String,
    model: String,
    imageUrl: String,
    onClick: () -> Unit
) {

    Card(onClick = onClick) {
        CoilNetworkImage(
            url = imageUrl,
            modifier = Modifier
                .height(150.dp)
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            contentScale = ContentScale.Crop,
            shape = MaterialTheme.shapes.medium
        )
        PlaneBanner(
            modifier = Modifier
                .padding(horizontal = 8.dp)
                .padding(bottom = 8.dp),
            manufacturer = manufacturer,
            model = model
        )

    }
}

@Composable
private fun PlaneBanner(
    modifier: Modifier = Modifier,
    manufacturer: String,
    model: String
) {
    Column(
        modifier = modifier.fillMaxWidth()
    ) {

        Text(
            text = manufacturer,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = model,
            style = MaterialTheme.typography.bodySmall
        )
    }
}

@Composable
@Preview
private fun PlaneCardLightPreview() {
    ChargeMapTheme {
        Column {
            PlaneCard(
                manufacturer = "Airbus",
                model = "A320",
                imageUrl = "",
                onClick = {}
            )
        }
    }
}

@Composable
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
private fun PlaneCardDarkPreview() {
    ChargeMapTheme {
        Column {
            PlaneCard(
                manufacturer = "Airbus",
                model = "A350",
                imageUrl = "",
                onClick = {},
            )
        }
    }
}