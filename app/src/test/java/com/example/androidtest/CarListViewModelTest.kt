package com.example.androidtest

import com.example.androidtest.api.GithubService
import com.example.androidtest.model.CarDataModel
import com.example.androidtest.utils.DelayConst
import com.example.androidtest.viewmodel.CarListViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import java.util.concurrent.TimeoutException

class CarListViewModelTest {
    private lateinit var viewModel: CarListViewModel

    @Mock
    private lateinit var repository: GithubService

    @Before
    fun setup() {
        Dispatchers.setMain(Dispatchers.Unconfined)
        MockitoAnnotations.openMocks(this)

        viewModel = CarListViewModel()
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun testFetchCarListSuccess() = runBlocking {
        val testCarList = listOf(CarDataModel("https://upload.wikimedia.org/wikipedia/commons/thumb/2/25/2015_Mazda_MX-5_ND_2.0_SKYACTIV-G_160_i-ELOOP_Rubinrot-Metallic_Vorderansicht.jpg/400px-2015_Mazda_MX-5_ND_2.0_SKYACTIV-G_160_i-ELOOP_Rubinrot-Metallic_Vorderansicht.jpg",
            "Mazda MX-5", "1989", "Sports car Roadster"))

        Mockito.`when`(repository.getCars()).thenReturn(testCarList)
        val startTime = System.currentTimeMillis()

        while (viewModel.carData.value == null) {
            if (System.currentTimeMillis() - startTime > DelayConst.TIMEOUT_IN_MILLIS) {
                throw TimeoutException("GithubApi didn't respond within 15 secs")
            }
            delay(100)
        }

        val result = viewModel.carData.value
        assert(result?.first()?.title == testCarList.first().title)
        assert(result?.first()?.category == testCarList.first().category)
        assert(result?.first()?.image == testCarList.first().image)
    }
}