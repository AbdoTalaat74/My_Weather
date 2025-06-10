package com.example.myweather.ui.screens.home

import com.example.myweather.models.Weather

data class WeatherState(
    val isLoading: Boolean? = null,
    val error: String? = null,
    val weatherInformation: Weather? = null
)