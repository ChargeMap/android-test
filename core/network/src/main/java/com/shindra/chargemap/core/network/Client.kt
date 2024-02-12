package com.shindra.chargemap.core.network

import io.ktor.client.engine.HttpClientEngineConfig
import io.ktor.client.engine.HttpClientEngineFactory
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.http.URLProtocol
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json


internal fun <C : HttpClientEngineConfig, T : HttpClientEngineFactory<C>> HttpClientFactory(
    engine: T,
    json: Json,
    engineConfiguration: C.() -> Unit
) = io.ktor.client.HttpClient(engine) {

    engine { engineConfiguration() }

    install(ContentNegotiation) {
        json(json)
    }

    defaultRequest {
        url {
            host = "api.api-ninjas.com/v1/aircraft"
            protocol = URLProtocol.HTTPS
        }
        headers.append("X-Api-Key", "HmRVesSiuT4RSHic4u+KPg==TttcNlfX2Yo3YYBL")
    }
}

