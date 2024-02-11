package com.shindra.acafsxb.core.network.di

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.shindra.acafsxb.core.network.R
import com.shindra.acafsxb.core.network.sources.*
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.ktor.client.*
import io.ktor.client.engine.okhttp.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
interface DataSourceModule {
    @Binds
    fun bindsAcafsxbNetwork(
        acafsxbNetwork: NinjaDataSourceNetwork
    ): NinjaDataSource

}

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideChuckerInterceptor(@ApplicationContext context: Context): ChuckerInterceptor {
        return ChuckerInterceptor.Builder(context).build()
    }

    @Provides
    @Singleton
    fun providesNetworkJson(): Json = Json { ignoreUnknownKeys = true }

    @Provides
    @Singleton
    fun provideAcafsxbHttpClient(
        @ApplicationContext context: Context,
        chuckerInterceptor: ChuckerInterceptor,
        json: Json
    ): HttpClient {
        return HttpClient(OkHttp) {
            engine {
                addInterceptor(chuckerInterceptor)
            }
            install(ContentNegotiation) {
                json(json)
            }
            defaultRequest {
                url {
                    host = context.getString(R.string.acafsxb_api_url)
                    protocol = URLProtocol.HTTPS
                }
                headers.append("X-Api-Key", "HmRVesSiuT4RSHic4u+KPg==TttcNlfX2Yo3YYBL")
            }
        }
    }
}