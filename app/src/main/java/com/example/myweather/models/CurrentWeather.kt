package com.example.myweather.models

data class CurrentWeather(
    val apparentTemperature: Int,
    val interval: Int,
    val isDay: Boolean,
    val rain: Int,
    val relativeHumidity: Int,
    val surfacePressure: Int,
    val temperature: Int,
    val uvIndex: Int,
    val weatherType: WeatherType,
    val windSpeed: Int
)