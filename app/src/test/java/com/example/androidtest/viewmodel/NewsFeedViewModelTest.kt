package com.example.androidtest.viewmodel

import com.example.androidtest.data.db.entity.ArticleEntity
import com.example.androidtest.di.DbRepository
import com.example.androidtest.di.NewsApiRepository
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.whenever


class NewsFeedViewModelTest {

    private lateinit var viewModel: NewsFeedViewModel
    private val mockedArticleEntity = ArticleEntity(
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
    private val dbRepository: DbRepository = mock()
    private val apiRepository: NewsApiRepository = mock()

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)

        viewModel = NewsFeedViewModel(apiRepository = apiRepository, dbRepository = dbRepository)
    }

    @Test
    fun `get top headlines should be empty`() = runBlocking {
        val flow: Flow<List<ArticleEntity>> = flow {
            emit(emptyList())
        }

        whenever(dbRepository.getAllArticles()).thenReturn(flow)

        viewModel.getTopHeadLines()

        assertThat(viewModel.topHeadlines.value.isEmpty()).isTrue()
    }


    @Test
    fun `get top headlines should not be empty`() = runBlocking {
        val flow: Flow<List<ArticleEntity>> = flow {
            emit(listOf(mockedArticleEntity))
        }

        whenever(dbRepository.getAllArticles()).thenReturn(flow)

        viewModel.getTopHeadLines()

        assertThat(viewModel.topHeadlines.value.isEmpty()).isFalse()
    }


    @Test
    fun `refresh top headlines leaves empty table`() = runTest {
        val flow: Flow<List<ArticleEntity>> = flow { emit(listOf(mockedArticleEntity)) }

        whenever(dbRepository.getAllArticles()).thenReturn(flow)

        dbRepository.clearTable()
        viewModel.getTopHeadLines()

        assertThat(viewModel.topHeadlines.value.isEmpty()).isTrue()
    }

    @Test
    fun `refresh top headlines clears table and refills it`() = runTest {
        val flow: Flow<List<ArticleEntity>> = flow { emit(listOf(mockedArticleEntity)) }

        whenever(dbRepository.getAllArticles()).thenReturn(flow)
        dbRepository.clearTable()
        viewModel.getTopHeadLines()

        assertThat(viewModel.topHeadlines.value.isEmpty()).isFalse()
    }
}
