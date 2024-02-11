package com.shindra.acafsxb.feature.balance

import com.shindra.acafsxb.core.model.BalanceType
import com.shindra.acafsxb.feature.balance.navigation.bo.FuelType
import com.shindra.acafsxb.feature.balance.navigation.calculator.AircraftCalculator
import com.shindra.acafsxb.feature.balance.navigation.calculator.CalculatorEntry
import org.junit.Before
import org.junit.Test

class AircraftMassCalculatorTests() {

    private lateinit var calculator: AircraftCalculator

    @Before
    fun setUp() {
        calculator = AircraftCalculator(
            fuelType = FuelType.AvGaz,
            emptyMass = 693.0,
            emptyMoment = 672.210,
            entries = listOf(
                CalculatorEntry(
                    value = 0.0,
                    lever = 0.941,
                    type = BalanceType.CREW
                ),
                CalculatorEntry(
                    value = 0.0,
                    lever = 1.85,
                    type = BalanceType.CREW
                ),
                CalculatorEntry(
                    value = 0.0,
                    lever = 2.41,
                    type = BalanceType.CARGO
                ),
                CalculatorEntry(
                    value = 15.0,
                    lever = 1.22,
                    type = BalanceType.FUEL
                )
            ),
            mtow = 758.0
        )
    }

    @Test
    fun testMasses() {
        val result = calculator.calculateCurrent()

        println()
    }


}
