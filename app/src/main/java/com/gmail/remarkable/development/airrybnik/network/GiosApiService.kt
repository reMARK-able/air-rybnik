package com.gmail.remarkable.development.airrybnik.network

import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET

private const val BASE_URL = "http://api.gios.gov.pl/pjp-api/rest/"

private val retrofit = Retrofit.Builder()
    .addConverterFactory(ScalarsConverterFactory.create())
    .baseUrl(BASE_URL)
    .build()

interface GiosApiService {
    @GET("data/getData/5466")
    suspend fun getPm10fromRybnik(): String
}

object GiosApi {
    val retrofiteService: GiosApiService by lazy {
        retrofit.create(GiosApiService::class.java)
    }
}