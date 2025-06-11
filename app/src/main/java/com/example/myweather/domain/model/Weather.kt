package com.example.myweather.domain.model

data class Weather(
    val currentWeather: CurrentWeather,
    val dailyWeather: List<DailyWeather>,
    val hourlyWeather: List<HourlyWeather>,
    val latitude: Double,
    val longitude: Double,
    val timezone: String,
)