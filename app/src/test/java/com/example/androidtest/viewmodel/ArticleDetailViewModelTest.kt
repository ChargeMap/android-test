package com.example.androidtest.viewmodel

import app.cash.turbine.test
import com.example.androidtest.Constants
import com.example.androidtest.data.db.entity.ArticleEntity
import com.example.androidtest.di.DbRepository
import com.example.androidtest.util.DefaultDispatcherProviderTest
import com.example.androidtest.util.DispatcherProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert.*

import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.doReturn

@RunWith(MockitoJUnitRunner::class)
class ArticleDetailViewModelTest {
    private val mockedArticleEntity = ArticleEntity(
        id = 0,
        country = Constants.COUNTRY_US,
        author = "toto",
        description = "Hello world",
        title = "News title",
        urlToImage = "https://image.cnbcfm.com/api/v1/image/106516157-1588351785968gettyimages-1209050413.jpeg?v=1695040122&w=1920&h=1080",
        content = "",
        publishedAt = "",
        url = "",
        source = null
    )

    @Mock
    private lateinit var dbRepository: DbRepository

    private lateinit var testDispatcher: DispatcherProvider

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp() {
        testDispatcher = DefaultDispatcherProviderTest()
        Dispatchers.setMain(testDispatcher.main)
    }

    @Test
    fun getArticleFromDb_shouldReturnSuccess() {
        runTest {
            doReturn(flowOf(mockedArticleEntity)).`when`(dbRepository).getArticleById(0)

            val viewModel = ArticleDetailViewModel(dbRepository, testDispatcher)
            viewModel.uiState.test {
                assertEquals(UiState.Loading(), expectMostRecentItem())
                assertEquals(UiState.Success(mockedArticleEntity), awaitItem())
                cancelAndIgnoreRemainingEvents()
            }

            viewModel.fetchArticle(0)
        }
    }
}