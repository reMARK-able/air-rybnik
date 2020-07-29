package com.gmail.remarkable.development.airrybnik.data.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [DatabaseSensorValue::class], version = 1, exportSchema = true)
abstract class SensorDatabase : RoomDatabase() {

    abstract fun sensorDao(): SensorDao
}