package com.shindra.chargemap.core.network.di

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.shindra.chargemap.core.network.HttpClientFactory
import com.shindra.chargemap.core.network.sources.NinjaDataSource
import com.shindra.chargemap.core.network.sources.NinjaDataSourceNetwork
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
    fun bindNinjaDataSource(
        ninjaDataSource: NinjaDataSourceNetwork
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
    fun provideOkHttpClient(
        chuckerInterceptor: ChuckerInterceptor,
        json: Json
    ) = HttpClientFactory(engine = OkHttp, json = json) {
        addInterceptor(chuckerInterceptor)
    }

}