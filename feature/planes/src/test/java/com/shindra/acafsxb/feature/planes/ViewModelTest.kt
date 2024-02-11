package com.shindra.acafsxb.feature.planes

import com.chargemap.core.domain.usecases.PlaneCategory
import com.chargemap.core.domain.usecases.PlaneCategoryType
import com.chargemap.core.domain.usecases.PlaneUseCase
import com.shindra.acafsxb.core.data.repositories.NinjaRepository
import com.shindra.acafsxb.core.designsystem.components.UiState
import com.shindra.acafsxb.core.model.ModelAirplane
import com.shindra.acafsxb.feature.planes.bo.SectionType
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ViewModelTest {

    private lateinit var vm: PlanesViewModel
    private lateinit var mockedUc: NinjaRepository
    private lateinit var testRepository: FakeRepository

    @Before
    fun before() {
        testRepository = FakeRepository()
        mockedUc = mockk(relaxed = true)
        vm = PlanesViewModel(PlaneUseCase(mockedUc))
    }

    @Test
    fun `test nominal`() = runTest {
        every { mockedUc.planesByEngineType(any()) } returns testRepository.modelAirplane()

        val j = backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            vm.planesByCategoryState.collect()
        }

        assert(vm.planesByCategoryState.value is UiState.Loading)

        testRepository.emit(listOf(ModelAirplane(manufacturer = "Boeing", model = "A350")))

        println((vm.planesByCategoryState.value))

        j.cancel()

    }
}


class FakeRepository() {
    val flow = MutableSharedFlow<List<ModelAirplane>>()
    suspend fun emit(value: List<ModelAirplane>) = flow.emit(value)

    fun modelAirplane(): Flow<List<ModelAirplane>> = flow
}