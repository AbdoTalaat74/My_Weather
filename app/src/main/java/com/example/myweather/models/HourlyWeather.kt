package com.example.myweather.models


data class HourlyWeather(
    val temperature: Int,
    val time: String,
    val weatherType: WeatherType
)