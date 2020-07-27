package com.gmail.remarkable.development.airrybnik.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface SensorDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(sensorValue: DatabaseSensorValue)

    @Update
    suspend fun update(sensorValue: DatabaseSensorValue)

    @Query("SELECT * FROM DatabaseSensorValue WHERE id = :latest")
    fun getLatest(latest: String = "latest"): LiveData<DatabaseSensorValue>

    @Query("DELETE FROM DatabaseSensorValue")
    suspend fun deleteAll()
}