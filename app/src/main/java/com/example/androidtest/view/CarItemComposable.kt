package com.example.androidtest.view

import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.androidtest.R
import com.example.androidtest.activities.DetailActivity
import com.example.androidtest.model.CarDataModel

@Composable
fun CarItemComposable(data: CarDataModel) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(dimensionResource(id = R.dimen.itemHeight))
            .padding(dimensionResource(id = R.dimen.spacing))
    ) {
        val context = LocalContext.current
        val painter = rememberAsyncImagePainter(
            model = ImageRequest.Builder(LocalContext.current)
                .data(data.image)
                .crossfade(true)
                .build(),
            contentScale = ContentScale.Crop
        )

        Image(
            painter = painter,
            contentDescription = data.title,
            modifier = Modifier
                .fillMaxWidth()
                .height(dimensionResource(id = R.dimen.itemHeight))
                .width(dimensionResource(id = R.dimen.itemHeight))
                .clip(MaterialTheme.shapes.medium)
                .clickable {
                    val titleParts = data.title.split(" ")
                    val intent = Intent(context, DetailActivity::class.java)

                    if (titleParts.size >= 2) {
                        intent.putExtra("make", titleParts[0])
                        intent.putExtra("model", titleParts[1])
                        intent.putExtra("year", data.startProduction)
                    }
                    context.startActivity(intent)
                },
            contentScale = ContentScale.Crop
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .align(Alignment.BottomStart)
                .background(
                    Brush.verticalGradient(
                        listOf(colorScheme.primary,
                            Color.Black)
                    ),
                    RectangleShape,
                    0.7f
                )
        ) {
            Text(
                text = data.title,
                color = colorScheme.onPrimary,
                modifier = Modifier.padding(start = dimensionResource(id = R.dimen.spacing), top = dimensionResource(id = R.dimen.spacing))
            )
            Text(
                text = data.category,
                color = colorScheme.onPrimary,
                modifier = Modifier
                    .padding(start = dimensionResource(id = R.dimen.spacing))
            )
        }
    }
}