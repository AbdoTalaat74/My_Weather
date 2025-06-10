package com.example.myweather.repo

import com.example.myweather.models.Weather

interface WeatherRepository {
    suspend fun getWeather(latitude: String, longitude: String): Result<Weather>
}