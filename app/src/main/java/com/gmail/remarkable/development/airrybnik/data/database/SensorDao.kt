package com.gmail.remarkable.development.airrybnik.data.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface SensorDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(sensorValue: DatabaseSensorValue)

    @Update
    suspend fun update(sensorValue: DatabaseSensorValue)

    @Query("SELECT * FROM DatabaseSensorValue WHERE id = :latest")
    fun observeLatest(latest: String = "latest"): LiveData<DatabaseSensorValue>

    @Query("SELECT * FROM DatabaseSensorValue WHERE id = :latest")
    suspend fun getLatest(latest: String = "latest"): DatabaseSensorValue?

    @Query("DELETE FROM DatabaseSensorValue")
    suspend fun deleteAll()
}