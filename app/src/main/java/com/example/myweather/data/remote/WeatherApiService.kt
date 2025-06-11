package com.example.myweather.data.remote

import com.example.myweather.data.remote.dto.WeatherResponse

interface WeatherApiService {
    suspend fun getWeather(latitude: Double, longitude: Double): Result<WeatherResponse>
}