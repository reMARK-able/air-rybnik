package com.gmail.remarkable.development.airrybnik.pm10

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gmail.remarkable.development.airrybnik.data.Repository
import kotlinx.coroutines.launch

class RybnikPm10ViewModel @ViewModelInject constructor(
    private val repository: Repository
) : ViewModel() {

    val response = repository.observeLatest()

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
                repository.refreshData()
            } catch (cause: Throwable) {
                _errorMessage.value = "${cause.message}" +
                        "\n${cause.cause}"
            } finally {
                _isLoading.value = false
            }
        }
    }
}