package com.gmail.remarkable.development.airrybnik.data

import androidx.lifecycle.LiveData
import com.gmail.remarkable.development.airrybnik.data.database.DatabaseSensorValue
import com.gmail.remarkable.development.airrybnik.data.database.SensorDao
import com.gmail.remarkable.development.airrybnik.data.network.GiosApiService
import com.gmail.remarkable.development.airrybnik.data.network.SensorValue
import com.gmail.remarkable.development.airrybnik.data.network.asDatabaseSensorValue
import com.gmail.remarkable.development.airrybnik.data.network.firstNonNull

class Repository(
    private val sensorDao: SensorDao,
    private val giosApiService: GiosApiService
) {

    fun observeLatest(): LiveData<DatabaseSensorValue> {
        return sensorDao.getLatest()
    }

    suspend fun refreshData(): SensorValue? {
        try {
            val response = giosApiService.getPm10fromRybnik()
            return when (val result = response.firstNonNull()) {
                null -> null
                else -> {
                    sensorDao.insert(result.asDatabaseSensorValue())
                    result
                }
            }
        } catch (cause: Throwable) {
            throw Throwable("Wystąpił błąd:", cause)
        }
    }
}