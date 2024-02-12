package com.shindra.chargemap.feature.planes.compose

import android.content.res.Configuration
import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.shindra.chargemap.core.designsystem.theme.ChargeMapTheme
import com.shindra.chargemap.feature.planes.R

@Composable
fun PlaneHeader(@StringRes title : Int) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.background)
            .padding(vertical = 8.dp)
    ) {
        Text(text = stringResource(id = title), Modifier.padding(horizontal = 8.dp) )
    }
}

@Preview
@Composable
fun PlaneHeaderLigthPreview(){
    ChargeMapTheme {
        PlaneHeader(title = R.string.plane_propjet_category_title)
    }

}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun PlaneHeaderDarkPreview(){
    ChargeMapTheme {
        PlaneHeader(title = R.string.plane_propjet_category_title)
    }
}