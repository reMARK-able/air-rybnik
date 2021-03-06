package com.gmail.remarkable.development.airrybnik.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.gmail.remarkable.development.airrybnik.data.network.SensorValue

@Entity
data class DatabaseSensorValue(
    @PrimaryKey
    val id: String = "latest",
    val date: String,
    val value: Double?
)

fun DatabaseSensorValue.asSensorValue(): SensorValue {
    return SensorValue(
        date,
        value
    )
}


