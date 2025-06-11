package com.example.myweather.presentation.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myweather.domain.repository.WeatherRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomeViewModel(
    private val weatherRepository: WeatherRepository,
) : ViewModel() {
    private var _weatherState = MutableStateFlow(WeatherState())
    val weatherState: StateFlow<WeatherState> = _weatherState

    private val _location = MutableStateFlow<Pair<Double, Double>?>(null)

    fun updateLocationAndGetWeather(lat: Double, lng: Double) {
        viewModelScope.launch {
            _location.value = Pair(lat, lng)
            getWeather(lat, lng)
        }
    }

    private suspend fun getWeather(lat: Double, lng: Double) {
        val result = weatherRepository.getWeather(lat, lng)
        result.fold(
            onSuccess = {
                _weatherState.value =
                    _weatherState.value.copy(isLoading = false, weatherInformation = it)
            },
            onFailure = {
                _weatherState.value =
                    _weatherState.value.copy(isLoading = false, error = it.message)
            }
        )
    }

}