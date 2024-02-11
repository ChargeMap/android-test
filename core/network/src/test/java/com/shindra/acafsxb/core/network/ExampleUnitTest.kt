package com.shindra.acafsxb.core.network

import com.shindra.acafsxb.core.network.sources.NinjaDataSourceNetwork
import io.ktor.client.engine.HttpClientEngineFactory
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.MockEngineConfig
import io.ktor.client.engine.mock.respond
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.headersOf
import io.ktor.utils.io.ByteReadChannel
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.json.Json
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {

    private val testData by lazy { TestData() }

    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }


    @Test
    fun `test aircraft fetching`() {
        val mockEngine = object : HttpClientEngineFactory<MockEngineConfig> {
            override fun create(block: MockEngineConfig.() -> Unit) = MockEngine {
                respond(
                    content = ByteReadChannel(testData.json),
                    status = HttpStatusCode.OK,
                    headers = headersOf(HttpHeaders.ContentType, "application/json")
                )
            }
        }

        val dataSource = NinjaDataSourceNetwork(HttpClientFactory(mockEngine, Json, {}))

        runTest {
            val r = dataSource.planeByEngineType("")
            assert(r.size == 3)
        }
    }
}

private class TestData {
    val json = "[\n" +
            "  {\n" +
            "    \"manufacturer\": \"Boeing\",\n" +
            "    \"model\": \"737 Max 7\",\n" +
            "    \"engine_type\": \"Jet\",\n" +
            "    \"max_speed_knots\": \"547\",\n" +
            "    \"ceiling_ft\": \"41000\",\n" +
            "    \"gross_weight_lbs\": \"177000\",\n" +
            "    \"length_ft\": \"116.7\",\n" +
            "    \"height_ft\": \"40.3\",\n" +
            "    \"wing_span_ft\": \"117.8\",\n" +
            "    \"range_nautical_miles\": \"3850\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"manufacturer\": \"Airbus\",\n" +
            "    \"model\": \"A318\",\n" +
            "    \"engine_type\": \"Jet\",\n" +
            "    \"max_speed_knots\": \"547\",\n" +
            "    \"ceiling_ft\": \"41000\",\n" +
            "    \"gross_weight_lbs\": \"149900\",\n" +
            "    \"length_ft\": \"103.0\",\n" +
            "    \"height_ft\": \"41.3\",\n" +
            "    \"wing_span_ft\": \"111.9\",\n" +
            "    \"range_nautical_miles\": \"3100\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"manufacturer\": \"Airbus\",\n" +
            "    \"model\": \"A319\",\n" +
            "    \"engine_type\": \"Jet\",\n" +
            "    \"max_speed_knots\": \"547\",\n" +
            "    \"ceiling_ft\": \"41000\",\n" +
            "    \"gross_weight_lbs\": \"166400\",\n" +
            "    \"length_ft\": \"110.9\",\n" +
            "    \"height_ft\": \"38.7\",\n" +
            "    \"wing_span_ft\": \"117.5\",\n" +
            "    \"range_nautical_miles\": \"3700\"\n" +
            "  }\n" +
            "]"
}