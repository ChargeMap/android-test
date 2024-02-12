package com.shindra.chargemap.feature.planedetails

import androidx.lifecycle.SavedStateHandle
import com.chargemap.core.domain.usecases.PlaneDetailsUseCase
import com.shindra.chargemap.core.designsystem.components.UiState
import com.shindra.chargemap.core.model.ModelDetailAirplane
import com.shindra.chargemap.feature.planedetails.navigation.manufacturerArgumentKey
import com.shindra.chargemap.feature.planedetails.navigation.modelArgumentKey
import com.shindra.chargemap.core.tests.MainDispatcherRule
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Test

import org.junit.Rule
import kotlin.time.Duration.Companion.seconds

class ViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule(UnconfinedTestDispatcher())
    private var mockedUc: PlaneDetailsUseCase = mockk<PlaneDetailsUseCase>()

    @Test
    fun success() = runTest {
        every { mockedUc.invoke("Test", "Test1") } returns flowOf(
            ModelDetailAirplane(
                manufacturer = "Airbus",
                model = "A350",
                engineType = "",
                engineThrustLbFt = null,
                maxSpeedKt = null,
                cruiseSpeedKt = null,
                ceilingFt = null,
                takeoffGroundRunFt = null,
                landingGroundRollFt = null,
                grossWeightLbs = null,
                emptyWeightLbs = null,
                lengthFt = null,
                heightFt = null,
                wingspanFt = null,
                rangeNauticalMiles = null
            )
        )

        val saveHandler = mockk<SavedStateHandle>()
        every {  saveHandler.get<String>(manufacturerArgumentKey) } returns "Test"
        every {  saveHandler.get<String>(modelArgumentKey) } returns "Test1"

        val vm = PlaneDetailsViewModel(mockedUc, saveHandler)

        val j = launch() {
            vm.planeDetailsState.collect()
        }

        assert(vm.planeDetailsState.value is UiState.Loading)
        delay(2.seconds)
        assert(vm.planeDetailsState.value is UiState.Success)

        val value = vm.planeDetailsState.value

        if (value is UiState.Success<List<PlaneDetailUi>>) {
            assert(value.value.size == 3)

            with(value.value.first()) {
                assert(key == R.string.plane_detail_manufacturer_title)
                assert(this.value == "Airbus")
                assert(this.unit == null)
            }
        }
        j.cancel()
    }

}