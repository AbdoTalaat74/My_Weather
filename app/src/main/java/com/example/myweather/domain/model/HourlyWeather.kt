package com.example.myweather.domain.model


data class HourlyWeather(
    val temperature: Int,
    val time: String,
    val weatherType: WeatherType
)