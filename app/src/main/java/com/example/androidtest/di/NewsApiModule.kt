package com.example.androidtest.di

import com.example.androidtest.Constants.API_KEY
import com.example.androidtest.Constants.BASE_URL
import com.example.androidtest.data.network.NewsApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NewsApiModule {

    @Singleton
    @Provides
    fun provideNewsApi(): NewsApi {
        return Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(provideOkHttpClient())
            .build()
            .create(NewsApi::class.java)
    }

     fun provideOkHttpClient(): OkHttpClient =
        OkHttpClient.Builder().apply {
            addInterceptor(Interceptor { chain ->
                val newRequest =
                    chain.request().newBuilder().addHeader("Authorization", API_KEY).build()
                chain.proceed(newRequest)
            })
        }.build()
}