package com.example.androidtest.api

import com.example.androidtest.UrlLoggingInterceptor
import com.example.androidtest.adapters.CarDetailAdapter
import com.example.androidtest.model.CarDetailModel
import com.squareup.moshi.Moshi
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface CarApiService {
    @GET("?cmd=getTrims")
    suspend fun retrieveCar(
        @Query("make") make: String,
        @Query("model") model: String,
        @Query("year") year: String
    ): CarDetailModel

    companion object {
        private var carApiService: CarApiService? = null
        fun getInstance() : CarApiService {
            if (carApiService == null) {
                val moshi = Moshi.Builder()
                    .add(CarDetailModel::class.java, CarDetailAdapter())
                    .build()

                val client = OkHttpClient.Builder()
                    .addInterceptor(UrlLoggingInterceptor())
                    .build()

                val retrofit = Retrofit.Builder()
                    .baseUrl("https://www.carqueryapi.com/api/0.3/")
                    .client(client)
                    .addConverterFactory(MoshiConverterFactory.create(moshi))
                    .build()
                carApiService = retrofit.create(CarApiService::class.java)
            }
            return carApiService!!
        }
    }
}