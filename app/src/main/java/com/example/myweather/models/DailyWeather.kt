package com.example.myweather.models


data class DailyWeather(
    val maxTemperature: Int,
    val minTemperature: Int,
    val dayOfWeek: String,
    val weatherType: WeatherType
)