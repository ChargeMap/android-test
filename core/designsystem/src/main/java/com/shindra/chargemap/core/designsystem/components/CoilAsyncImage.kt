package com.shindra.chargemap.core.designsystem.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.DefaultAlpha
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import coil.compose.SubcomposeAsyncImage

@Composable
fun CoilNetworkImage(
    url: String,
    modifier: Modifier = Modifier,
    onLoading: @Composable () -> Unit = { ShimmerSurface(Modifier.fillMaxSize()) },
    onError: @Composable () -> Unit = {},
    contentDescription: String? = null,
    alignment: Alignment = Alignment.Center,
    contentScale: ContentScale = ContentScale.Fit,
    alpha: Float = DefaultAlpha,
    colorFilter: ColorFilter? = null,
    shape: Shape = RectangleShape
) {
    Surface(
        shape = shape,
        modifier = modifier
    ) {
        SubcomposeAsyncImage(
            model = url,
            contentDescription = contentDescription,
            loading = { onLoading() },
            error = { onError() },
            contentScale = contentScale,
            alignment = alignment,
            alpha = alpha,
            colorFilter = colorFilter
        )
    }
}