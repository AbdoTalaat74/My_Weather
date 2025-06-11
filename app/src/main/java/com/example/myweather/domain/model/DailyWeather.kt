package com.example.myweather.domain.model


data class DailyWeather(
    val maxTemperature: Int,
    val minTemperature: Int,
    val dayOfWeek: String,
    val weatherType: WeatherType
)