package com.example.androidtest

import com.example.androidtest.api.CarApiService
import com.example.androidtest.model.CarDetailModel
import com.example.androidtest.utils.DelayConst
import com.example.androidtest.viewmodel.CarDetailViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
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

@ExperimentalCoroutinesApi

class CarDetailViewModelTest {
    private lateinit var viewModel: CarDetailViewModel

    @Mock
    private lateinit var repository: CarApiService

    @Before
    fun setup() {
        Dispatchers.setMain(Dispatchers.Unconfined)

        // Initialize mock annotation
        MockitoAnnotations.openMocks(this)

        viewModel = CarDetailViewModel("ford", "mustang", "2022")
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun testFetchCarDetailSuccess() = runBlocking {
        val testCarDetail = CarDetailModel(
            modelId = "81690",
            modelMakeId = "Ford",
            modelName = "Mustang",
            modelTrim = "EcoBoost 2dr Coupe (2.3L 4cyl Turbo 6M)",
            modelYear = "2022"
        )

        val params = mapOf(
            "make" to "Ford",
            "model" to "Mustang",
            "year" to "2022"
        )

        Mockito.`when`(repository.retrieveCar(params)).thenReturn(testCarDetail)
        val startTime = System.currentTimeMillis()

        while (viewModel.carData.value == null) {
            if (System.currentTimeMillis() - startTime > DelayConst.TIMEOUT_IN_MILLIS) {
                throw TimeoutException("CarApi didn't respond within 15 secs")
            }
            delay(100)
        }

        val result = viewModel.carData.value
        assert(result?.modelId == testCarDetail.modelId)
        assert(result?.modelMakeId == testCarDetail.modelMakeId)
        assert(result?.modelName == testCarDetail.modelName)
    }
}
