package com.gmail.remarkable.development.airrybnik.network

import retrofit2.http.GET

interface GiosApiService {
    @GET("data/getData/5466")
    suspend fun getPm10fromRybnik(): GiosSensorData
}