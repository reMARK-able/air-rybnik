package com.gmail.remarkable.development.airrybnik.data

import androidx.lifecycle.LiveData
import com.gmail.remarkable.development.airrybnik.data.database.DatabaseSensorValue
import com.gmail.remarkable.development.airrybnik.data.database.SensorDao
import com.gmail.remarkable.development.airrybnik.data.network.GiosApiService

class Repository(
    private val sensorDao: SensorDao,
    private val giosApiService: GiosApiService
) {

    fun observeLatest(): LiveData<DatabaseSensorValue> {
        return sensorDao.getLatest()
    }
}