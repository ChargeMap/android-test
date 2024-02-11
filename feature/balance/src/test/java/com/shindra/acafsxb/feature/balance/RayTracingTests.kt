package com.shindra.acafsxb.feature.balance

import com.shindra.acafsxb.feature.balance.navigation.calculator.Edge
import com.shindra.acafsxb.feature.balance.navigation.calculator.Figure
import com.shindra.acafsxb.feature.balance.navigation.calculator.RayPoint
import org.junit.Test
import kotlin.system.measureTimeMillis

class RayTracingTests {

    private val polygon = Figure(
        edges = listOf(
            Edge(
                s = RayPoint(0.8901, 500.0),
                e = RayPoint(1.201, 500.0)
            ),
            Edge(
                s = RayPoint(1.201, 500.0),
                e = RayPoint(1.201, 1043.0)
            ),
            Edge(
                s = RayPoint(1.20, 1043.0),
                e = RayPoint(0.9801, 1043.0)
            ),
            Edge(
                s = RayPoint(0.9801, 1043.0),  
                e = RayPoint(0.8901, 885.0)
            ),
            Edge(
                s = RayPoint(0.8901, 885.0),
                e = RayPoint(0.8901, 500.0)
            )


        )
    )

    private val square = Figure(
        listOf(
            Edge(s = RayPoint(0.0, 0.0), e = RayPoint(10.0, 0.0)),
            Edge(s = RayPoint(10.0, 0.0), e = RayPoint(10.0, 10.0)),
            Edge(s = RayPoint(10.0, 10.0), e = RayPoint(0.0, 10.0)),
            Edge(s = RayPoint(0.0, 10.0), e = RayPoint(0.0, 0.0))
        )
    )


    @Test
    fun pointInside() {
        val b = square.contains(RayPoint(9.99, 9.99))

            val b1 = polygon.contains(RayPoint(0.89,600.0))

        println()
    }


}