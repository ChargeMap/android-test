package com.example.androidtest.ui.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.androidtest.R
import com.example.androidtest.data.db.entity.ArticleEntity
import com.example.androidtest.ui.theme.AndroidTestTheme

private val mockedArticle = ArticleEntity(
    author = "toto",
    description = "Hello world",
    title = "News title",
    urlToImage = "https://image.cnbcfm.com/api/v1/image/106516157-1588351785968gettyimages-1209050413.jpeg?v=1695040122&w=1920&h=1080",
    content = "",
    publishedAt = "",
    url = "",
    source = null
)

@OptIn(ExperimentalGlideComposeApi::class, ExperimentalMaterial3Api::class)
@Composable
fun ArticleCardView(
    navController: NavController? = null,
    article: ArticleEntity,
    isDetails: Boolean = false
) {
    val modifier = Modifier.padding(dimensionResource(id = R.dimen.default_padding))

    if (isDetails) {
        val scrollState = rememberScrollState()
        modifier
            .verticalScroll(scrollState)
            .wrapContentHeight()
    }

    ElevatedCard(
        modifier = modifier,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        ),
        onClick = {
            if (!isDetails) {
                navController?.navigate("details/${article.id}")
            }
        }
    ) {
        Column(
            modifier = Modifier.padding(dimensionResource(id = R.dimen.default_padding)),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = article.title,
                fontSize = 20.sp,
                textAlign = TextAlign.Start,
                fontWeight = FontWeight.Bold
            )

            GlideImage(
                modifier = Modifier
                    .padding(dimensionResource(id = R.dimen.default_padding))
                    .fillMaxWidth()
                    .height(dimensionResource(id = R.dimen.article_image_height)),
                model = article.urlToImage,
                contentDescription = "",
                alignment = Alignment.Center,
                contentScale = ContentScale.FillWidth
            )

            Text(
                modifier = Modifier.padding(bottom = dimensionResource(id = R.dimen.default_padding)),
                text = if (isDetails) {
                    article.content
                } else {
                    article.description
                },
                textAlign = TextAlign.Justify
            )

            ArticleAuthorAndPublicationView(article = article)
        }
    }
}

@Composable
fun ArticleAuthorAndPublicationView(article: ArticleEntity) {
    Row(
        modifier = Modifier.padding(bottom = dimensionResource(id = R.dimen.large_padding)),
        horizontalArrangement = Arrangement.Start
    ) {
        Text(
            modifier = Modifier.padding(end = dimensionResource(id = R.dimen.default_padding)),
            text = article.author,
            textAlign = TextAlign.Start,
            fontSize = 10.sp,
            fontStyle = FontStyle.Italic
        )

        Text(
            text = article.publishedAt,
            textAlign = TextAlign.Start,
            fontSize = 10.sp
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ArticleCardViewPreview() {
    AndroidTestTheme {
        ArticleCardView(article = mockedArticle)
    }
}

@Preview
@Composable
fun ArticleAuthorAndPublicationViewPreview() {
    AndroidTestTheme {
        ArticleAuthorAndPublicationView(mockedArticle)
    }
}