package com.shindra.acafsxb.feature.balance.navigation.calculator

import android.graphics.Point
import android.util.Log
import java.lang.Double.MAX_VALUE
import java.lang.Double.MIN_VALUE
import java.lang.Math.abs


data class RayPoint(val x: Double, val y: Double)
data class Edge(val s: RayPoint, val e: RayPoint) {
    operator fun invoke(p: RayPoint): Boolean = when {
        s.y > e.y -> {
           println("Edge inversion")
            Edge(e, s).invoke(p)
        }
        p.y == s.y || p.y == e.y ->{
            println("New point with epislon on y")
            invoke(RayPoint(p.x, p.y + epsilon))
        }
        p.y > e.y || p.y < s.y || p.x > Math.max(s.x, e.x) -> {
            println("max")
            false
        }
        p.x < Math.min(s.x, e.x) -> {
            println("min")
            true
        }
        else -> {
            println("blue red")
            val blue = if (abs(s.x - p.x) > MIN_VALUE) (p.y - s.y) / (p.x - s.x) else MAX_VALUE
            val red = if (abs(s.x - e.x) > MIN_VALUE) (e.y - s.y) / (e.x - s.x) else MAX_VALUE
            blue >= red
        }
    }

    val epsilon = 0.00001
}

//Edge must be set in counter clock rotation on the x axis
data class Figure(
    val edges: List<Edge>
){
    operator fun contains(p: RayPoint) :Boolean{
        return edges.count({ it(p) }) % 2 != 0
    }
}