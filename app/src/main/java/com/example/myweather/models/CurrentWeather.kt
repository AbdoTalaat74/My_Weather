package com.example.myweather.models

data class CurrentWeather(
    val apparentTemperature: Double,
    val interval: Int,
    val isDay: Boolean,
    val rain: Int,
    val relativeHumidity: Int,
    val surfacePressure: Double,
    val temperature: Double,
    val uvIndex: Double,
    val weatherType: WeatherType,
    val windSpeed: Double
)