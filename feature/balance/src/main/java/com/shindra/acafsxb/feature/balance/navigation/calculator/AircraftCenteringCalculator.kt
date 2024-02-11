package com.shindra.acafsxb.feature.balance.navigation.calculator

import com.shindra.acafsxb.core.model.BalanceType

import com.shindra.acafsxb.feature.balance.navigation.bo.FuelType
import kotlin.math.roundToInt

typealias X = Float
typealias Y = Float



internal data class CalculatorEntry(
    var value: Double,
    val type: BalanceType,
    val lever: Double
)

internal data class CalculatorResult(
    val currentMass: Int,
    val remainingMass: Int,
    val remainingFuel: Int,
    val balanceLine: List<Pair<X, Y>>
)

internal class AircraftCalculator(
    private val fuelType: FuelType,
    private val emptyMass: Double,
    private val mtow: Double,
    private val emptyMoment: Double,
    private val entries: List<CalculatorEntry>
) {

    fun calculate(value: Float, type: BalanceType): CalculatorResult {
        actualise(value, type)
        return calculateCurrent()
    }

    fun calculateCurrent(): CalculatorResult {
        val currentMass = entries.calculateTotalMass()
        val remainingMass = (mtow - currentMass).roundToInt()
        return CalculatorResult(
            currentMass = currentMass,
            remainingMass = remainingMass,
            balanceLine = calculateBalanceLine(),
            remainingFuel = calculateFuelPossible(remainingMass)
        )
    }

    private fun actualise(value: Float, type: BalanceType) {
        entries.firstOrNull { it.type == type }?.value = value.toDouble()
    }

    private fun List<CalculatorEntry>.calculateTotalMass(): Int {
        return sumOf {
            calculateMass(it.value, it.type)
        }.roundToInt() + emptyMass.roundToInt()
    }

    private fun List<CalculatorEntry>.calculateTotalMoment(): Double {
        return sumOf {
            calculateMass(it.value, it.type) * it.lever
        } + emptyMoment
    }

    private fun calculateFuelPossible(remainingMass: Int): Int {
        return (remainingMass / fuelType.volumetricMass).roundToInt()
    }

    /**+
     * First point is with fuel at max
     * Second point is with current masses input
     * Last point is with no fuel
     *
     * X is the moment, y is the total mass
     *
     */
    private fun calculateBalanceLine(): List<Pair<Float, Float>> {
        return listOf(
            //Current
            calculateBalanceLeverPoint(entries),
            //Without Fuel
            calculateBalanceLeverPoint(entries.filter { it.type != BalanceType.FUEL })
        )
    }

    private fun calculateBalanceLeverPoint(entries: List<CalculatorEntry>): Pair<Float, Float> {
        val mass = entries.calculateTotalMass().toFloat()
        val moment = entries.calculateTotalMoment().toFloat()
        return Pair(
            first = moment / mass,
            second = mass
        )
    }

    private fun calculateMass(value: Double, type: BalanceType): Double {
        return when (type) {
            BalanceType.FUEL -> value * fuelType.volumetricMass
            else -> value
        }
    }
}