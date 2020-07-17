package com.gmail.remarkable.development.airrybnik

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gmail.remarkable.development.airrybnik.network.GiosApi
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    private val _response = MutableLiveData<String>()
    val response: LiveData<String>
        get() = _response

    init {
        getGiosPm10fromRybnik()
    }

    private fun getGiosPm10fromRybnik() {
        viewModelScope.launch {
            try {
                _response.value = GiosApi.retrofitService.getPm10fromRybnik()
            } catch (e: Exception) {
                _response.value = "Error: ${e.message}" +
                        "\n Cause: ${e.cause}"
            }
        }

    }
}