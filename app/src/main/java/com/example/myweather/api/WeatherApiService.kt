package com.example.myweather.api

import com.example.myweather.api.dto.WeatherResponse

interface WeatherApiService {
    suspend fun getWeather(latitude: String, longitude: String): Result<WeatherResponse>
}