package com.shindra.chargemap.core.designsystem.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.WarningAmber
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.shindra.chargemap.core.designsystem.theme.ChargeMapTheme

@Composable
fun FullScreenErrorScreen(onRetry: () -> Unit) {
    Scaffold(modifier = Modifier.fillMaxSize(), bottomBar = {
        Button(
            onClick = onRetry, modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Text("Réessayer ")
        }
    }) {

        Row(
            modifier = Modifier
                .padding(it)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Image(
                    imageVector = Icons.Default.WarningAmber,
                    contentDescription = null,
                    colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.error),
                    modifier = Modifier.size(100.dp)
                )

                Text(
                    text = "Upssie",
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.error
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun FullScreenErrorScreenPreview() {
    ChargeMapTheme {
        FullScreenErrorScreen({})
    }
}