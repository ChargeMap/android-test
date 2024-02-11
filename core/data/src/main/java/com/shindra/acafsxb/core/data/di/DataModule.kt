package com.shindra.acafsxb.core.data.di

import com.shindra.acafsxb.core.data.repositories.NinjaRepository
import com.shindra.acafsxb.core.network.sources.*
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
    fun provideAcafsxbRepository(client: NinjaDataSource) = NinjaRepository(client)

}