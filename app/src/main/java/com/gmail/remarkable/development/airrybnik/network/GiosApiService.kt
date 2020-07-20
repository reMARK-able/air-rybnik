package com.gmail.remarkable.development.airrybnik.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

private const val BASE_URL = "http://api.gios.gov.pl/pjp-api/rest/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

interface GiosApiService {
    @GET("data/getData/5466")
    suspend fun getPm10fromRybnik(): GiosSensorData
}

object GiosApi {
    val retrofitService: GiosApiService by lazy {
        retrofit.create(GiosApiService::class.java)
    }
}