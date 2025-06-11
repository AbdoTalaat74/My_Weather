package com.example.myweather.presentation.ui.screens.home

import com.example.myweather.domain.model.Weather

data class WeatherState(
    val isLoading: Boolean = true,
    val error: String? = null,
    val weatherInformation: Weather? = null
)