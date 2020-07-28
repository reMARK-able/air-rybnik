package com.gmail.remarkable.development.airrybnik.pm10

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gmail.remarkable.development.airrybnik.database.SensorDatabase
import com.gmail.remarkable.development.airrybnik.network.GiosApiService
import com.gmail.remarkable.development.airrybnik.network.asDatabaseSensorValue
import com.gmail.remarkable.development.airrybnik.network.firstNonNull
import kotlinx.coroutines.launch

class RybnikPm10ViewModel @ViewModelInject constructor(
    private val giosApiService: GiosApiService,
    private val database: SensorDatabase
) : ViewModel() {

    val response = database.sensorDao().getLatest()

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String>
        get() = _errorMessage

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    fun getGiosPm10fromRybnik() {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                val response = giosApiService.getPm10fromRybnik()
                response.firstNonNull()?.let {
                    database.sensorDao().insert(it.asDatabaseSensorValue())
                }
            } catch (e: Exception) {
                _errorMessage.value = "Error: ${e.message}" +
                        "\n Cause: ${e.cause}"
            } finally {
                _isLoading.value = false
            }
        }
    }
}