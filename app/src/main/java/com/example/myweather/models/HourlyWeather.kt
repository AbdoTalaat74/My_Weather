package com.example.myweather.models


data class HourlyWeather(
    val temperature: Double,
    val time: String,
    val weatherType: WeatherType
)