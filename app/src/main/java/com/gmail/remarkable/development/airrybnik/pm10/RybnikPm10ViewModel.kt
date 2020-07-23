package com.gmail.remarkable.development.airrybnik.pm10

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gmail.remarkable.development.airrybnik.network.GiosApiService
import com.gmail.remarkable.development.airrybnik.network.GiosSensorData
import kotlinx.coroutines.launch

class RybnikPm10ViewModel @ViewModelInject constructor(
    private val giosApiService: GiosApiService
) : ViewModel() {

    private val _response = MutableLiveData<GiosSensorData>()
    val response: LiveData<GiosSensorData>
        get() = _response

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String>
        get() = _errorMessage

    init {
        getGiosPm10fromRybnik()
    }

    private fun getGiosPm10fromRybnik() {
        viewModelScope.launch {
            try {
                _response.value = giosApiService.getPm10fromRybnik()
            } catch (e: Exception) {
                _errorMessage.value = "Error: ${e.message}" +
                        "\n Cause: ${e.cause}"
            }
        }

    }
}