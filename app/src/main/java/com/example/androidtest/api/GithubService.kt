package com.example.androidtest.api

import com.example.androidtest.adapters.CarListAdapter
import com.example.androidtest.model.CarDataModel
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET

interface GithubService {
    @GET("cars.json")
    suspend fun getCars(): List<CarDataModel>

    companion object {
        private var githubService: GithubService? = null
        fun getInstance() : GithubService {
            if (githubService == null) {
                val moshi = Moshi.Builder()
                    .add(KotlinJsonAdapterFactory())
                    .add(CarListAdapter())
                    .build()

                val retrofit = Retrofit.Builder()
                    .baseUrl("https://raw.githubusercontent.com/itemsapi/itemsapi-example-data/master/items/")
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addConverterFactory(MoshiConverterFactory.create(moshi))
                    .build()
                githubService = retrofit.create(GithubService::class.java)
            }
            return githubService!!
        }
    }
}