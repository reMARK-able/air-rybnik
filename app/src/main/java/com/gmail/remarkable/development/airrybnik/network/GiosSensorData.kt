package com.gmail.remarkable.development.airrybnik.network

import com.squareup.moshi.Json

data class GiosSensorData(
    @Json(name = "key")
    val name: String,
    val values: List<SensorValue>
)

data class SensorValue(
    val date: String,
    val value: Double?
)

fun GiosSensorData.firstNonNull() = this.values.firstOrNull { it.value != null }
