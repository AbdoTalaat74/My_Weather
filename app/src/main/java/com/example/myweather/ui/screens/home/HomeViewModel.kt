package com.example.myweather.ui.screens.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myweather.repo.WeatherRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlin.math.ln

class HomeViewModel(
    private val weatherRepository: WeatherRepository,
) : ViewModel() {
    private var _weatherState = MutableStateFlow(WeatherState())
    val weatherState: StateFlow<WeatherState> = _weatherState

    private val _location = MutableStateFlow<Pair<String, String>?>(null)

    fun updateLocationAndGetWeather(lat: String, lng: String) {
        viewModelScope.launch {
            _location.value = Pair(lat, lng)
            getWeather(lat, lng)
        }
    }

    private suspend fun getWeather(lat: String, lng: String) {
        val result = weatherRepository.getWeather(lat, lng)

        result.fold(
            onSuccess = {
                Log.e("HomeViewModelLog", "Weather fetched successfully")
                _weatherState.value =
                    _weatherState.value.copy(isLoading = false, weatherInformation = it)
            },
            onFailure = {
                Log.e("HomeViewModelLog", "Failed to fetch data ${it.message}")
            }
        )
    }

}