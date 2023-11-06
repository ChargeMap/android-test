package com.example.androidtest.view

import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
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
import com.example.androidtest.activities.DetailActivity
import com.example.androidtest.model.CarDataModel

@Composable
fun CarItemComposable(data: CarDataModel) {
    val context = LocalContext.current
    val painter = rememberAsyncImagePainter(
        model = ImageRequest.Builder(LocalContext.current)
            .data(data.image)
            .crossfade(true)
            .build(),
        contentScale = ContentScale.Crop
    )

    Column(
        modifier = Modifier.clickable {
            val titleParts = data.title.split(" ")
            val intent = Intent(context, DetailActivity::class.java)

            if (titleParts.size >= 2) {
                intent.putExtra("make", titleParts[0])
                intent.putExtra("model", titleParts[1])
                intent.putExtra("year", data.startProduction)
            }
            context.startActivity(intent)
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