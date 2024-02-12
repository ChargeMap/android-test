package com.shindra.chargemap.core.data.di

import com.shindra.chargemap.core.data.repositories.NinjaRepository
import com.shindra.chargemap.core.network.sources.NinjaDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object DataModule {
    @Provides
    @Singleton
    fun provideNinjaRepository(client: NinjaDataSource) = NinjaRepository(client)

}