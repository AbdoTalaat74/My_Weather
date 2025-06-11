package com.example.myweather.domain.repository

import com.example.myweather.domain.model.Weather

interface WeatherRepository {
    suspend fun getWeather(latitude: Double, longitude: Double): Result<Weather>
}