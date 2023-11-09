package com.example.androidtest.viewmodel

import app.cash.turbine.test
import com.example.androidtest.Constants
import com.example.androidtest.data.db.entity.ArticleEntity
import com.example.androidtest.di.DbRepository
import com.example.androidtest.di.NewsApiRepository
import com.example.androidtest.util.DefaultDispatcherProviderTest
import com.example.androidtest.util.DispatcherProvider
import com.example.androidtest.viewmodel.feed.NewsFeedViewModel
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.doReturn

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class NewsFeedViewModelTest {
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

    @Mock
    private lateinit var apiRepository: NewsApiRepository

    private lateinit var testDispatcher: DispatcherProvider

    @Before
    fun setup() {
        testDispatcher = DefaultDispatcherProviderTest()
        Dispatchers.setMain(testDispatcher.main)
    }


    @Test
    fun getTopHeadlines200_shouldReturnSuccess() {
        runTest {
            doReturn(flowOf(listOf(mockedArticleEntity))).`when`(dbRepository).getAllArticlesByCountry(Constants.COUNTRY_US)

            val viewModel = NewsFeedViewModel(apiRepository, dbRepository, testDispatcher)
            viewModel.uiState.test {
                assertEquals(UiState.Success(listOf(mockedArticleEntity)), awaitItem())
                cancelAndIgnoreRemainingEvents()
            }
            viewModel.refresh()
        }
    }


    @Test
    fun getTopHeadlinesError_whenFetch_shouldReturnError() {
        runTest {
            val errorMessage = "Error Message For You"
            doReturn(flow<List<ArticleEntity>> {
                throw IllegalStateException(errorMessage)
            }).`when`(dbRepository).getAllArticlesByCountry(Constants.COUNTRY_US)

            val viewModel = NewsFeedViewModel(apiRepository, dbRepository, testDispatcher,)
            viewModel.uiState.test {
                assertEquals(
                    UiState.Error(
                        "NewsFeedViewModel -----> exception handler: ${
                            IllegalStateException(
                                errorMessage
                            )
                        }"
                    ),
                    awaitItem()
                )
                cancelAndIgnoreRemainingEvents()
            }
            viewModel.refresh()
        }
    }

    @After
    fun tearDown() {
    }
}
