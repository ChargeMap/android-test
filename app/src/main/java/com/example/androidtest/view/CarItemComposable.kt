package com.example.androidtest.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.navigation.compose.rememberNavController
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.androidtest.model.CarDataModel

@Composable
fun CarItemComposable(data: CarDataModel) {
    val navController = rememberNavController()

    val painter = rememberAsyncImagePainter(
        model = ImageRequest.Builder(LocalContext.current)
            .data(data.image)
            .crossfade(true)
            .build(),
        contentScale = ContentScale.Crop
    )

    Column(
        modifier = Modifier.clickable {
            // TODO Handle click
        }
    ) {
        Image(
            painter = painter,
            contentDescription = data.title,
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .width(200.dp)
                .clip(MaterialTheme.shapes.medium),
            contentScale = ContentScale.Crop
        )
        Text(text = data.title)
        Text(text = data.category)
    }
}