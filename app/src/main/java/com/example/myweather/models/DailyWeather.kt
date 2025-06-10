package com.example.myweather.models


data class DailyWeather(
    val maxTemperature: Double,
    val minTemperature: Double,
    val dayOfWeek: String,
    val weatherType: WeatherType
)