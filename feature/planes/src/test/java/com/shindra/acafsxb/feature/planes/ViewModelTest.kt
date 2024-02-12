package com.shindra.acafsxb.feature.planes

import com.chargemap.core.domain.usecases.PlaneCategory
import com.chargemap.core.domain.usecases.PlaneCategoryType
import com.chargemap.core.domain.usecases.PlaneUseCase
import com.shindra.acafsxb.core.designsystem.components.UiState
import com.shindra.acafsxb.feature.planes.bo.ListModel
import com.shindra.chargemap.core.tests.MainDispatcherRule
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import kotlin.time.Duration.Companion.seconds

class ViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule(UnconfinedTestDispatcher())
    private var mockedUc: PlaneUseCase = mockk<PlaneUseCase>()

    @Test
    fun `success`() = runTest {
        every { mockedUc.invoke() } returns flowOf(
            listOf(
                PlaneCategory(
                    category = PlaneCategoryType.Jet,
                    planes = emptyList()
                )
            )
        )

        val vm = PlanesViewModel(mockedUc)

        val j = launch() {
            vm.planesByCategoryState.collect()
        }


        assert(vm.planesByCategoryState.value is UiState.Loading)
        delay(2.seconds)
        assert(vm.planesByCategoryState.value is UiState.Success)

        val value = vm.planesByCategoryState.value

        if(value is UiState.Success<List<ListModel>>){
            assert(value.value.size == 1)
            assert(value.value.first().title == R.string.plane_jet_category_title)
        }

        j.cancel()
    }
}