package com.shindra.acafsxb.feature.planes

import com.chargemap.core.domain.usecases.PlaneCategory
import com.chargemap.core.domain.usecases.PlaneCategoryType
import com.shindra.acafsxb.core.model.ModelAirplane
import com.shindra.acafsxb.feature.planes.bo.SectionType
import kotlinx.coroutines.Delay
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.produceIn
import kotlinx.coroutines.flow.zip
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test
import kotlin.system.measureTimeMillis

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }


    @Test
    fun `category to section item`() {
        val category = listOf<PlaneCategory>(
            PlaneCategory(
                PlaneCategoryType.Jet,
                listOf(ModelAirplane("Test1", ""), ModelAirplane("Test2", ""))
            ),
            PlaneCategory(
                PlaneCategoryType.PropJet,
                listOf(ModelAirplane("Test1", ""), ModelAirplane("Test2", ""))
            )
        )

        val flatten = category.flatMap {
            buildList {

                add(SectionType.Header(0))
                addAll(it.planes.map { p -> SectionType.Item(p) })
            }
        }

        println(flatten)

    }
}

