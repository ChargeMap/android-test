package com.shindra.chargemap.core.designsystem.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.shindra.chargemap.core.designsystem.theme.ChargeMapTheme
import com.shindra.chargemap.core.designsystem.R

@Composable
fun FullLoadingScreen(text: String = stringResource(id = R.string.full_screen_loading_waiting_text)) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        CircularProgressIndicator()
        Text(
            text = text,
            color = ProgressIndicatorDefaults.circularColor,
            modifier = Modifier.padding(16.dp)
        )
    }
}


@Composable
@Preview
fun FullLoadingScreenPReview() {
    ChargeMapTheme() {
        FullLoadingScreen()
    }
}