package com.shindra.acafsxb.core.designsystem.components

import android.graphics.Bitmap
import android.graphics.pdf.PdfRenderer
import android.net.Uri
import android.os.ParcelFileDescriptor
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.core.net.toFile
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import kotlin.math.roundToInt

@Composable
fun PdfReader(
    pdfUri: Uri
) {

    val renderer = remember(pdfUri) {
        val input = ParcelFileDescriptor.open(pdfUri.toFile(), ParcelFileDescriptor.MODE_READ_ONLY)
        PdfRenderer(input)
    }



    BoxWithConstraints(modifier = Modifier.fillMaxHeight()) {
        val width = with(LocalDensity.current) { maxWidth.toPx() }.toInt()
        val height = with(LocalDensity.current) { maxHeight.toPx() }.toInt()

        LazyColumn(
            modifier = Modifier.fillMaxHeight()
        ) {
            items(count = renderer.pageCount) {

                val destinationBitmap = renderer.openPage(it).use {
                    val h = it.height
                    val w = it.width

                    val scale: Float =
                        Math.min(width.toFloat() / w, height.toFloat() / h)

                    val bitmap = Bitmap.createBitmap(
                        (w * scale).roundToInt(),
                        (h * scale).roundToInt(),
                        Bitmap.Config.ARGB_8888
                    )

                    it.render(
                        bitmap,
                        null,
                        null,
                        PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY
                        )

                    bitmap
                }

                val request = ImageRequest.Builder(LocalContext.current)
                    .size(8500, 8500)
                    .data(destinationBitmap)
                    .build()

                Image(
                    modifier = Modifier.fillMaxSize(),
                    painter = rememberAsyncImagePainter(request),
                    contentDescription = null
                )


            }
        }
    }

}

