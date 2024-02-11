package com.chargemap.core.domain.di

import com.chargemap.core.domain.usecases.PlaneUseCase
import com.shindra.acafsxb.core.data.repositories.NinjaRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object DomainDiModule {
    @Provides
    @Singleton
    fun providePlaneUsesCase(repository: NinjaRepository) = PlaneUseCase(repository)

}