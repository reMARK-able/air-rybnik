package com.gmail.remarkable.development.airrybnik

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gmail.remarkable.development.airrybnik.network.GiosApi
import com.gmail.remarkable.development.airrybnik.network.GiosSensorData
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

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
                _response.value = GiosApi.retrofitService.getPm10fromRybnik()
            } catch (e: Exception) {
                _errorMessage.value = "Error: ${e.message}" +
                        "\n Cause: ${e.cause}"
            }
        }

    }
}