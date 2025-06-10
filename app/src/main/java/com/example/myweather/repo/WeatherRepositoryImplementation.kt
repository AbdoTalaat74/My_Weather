package com.example.myweather.repo

import com.example.myweather.api.WeatherApiService
import com.example.myweather.api.dto.toWeather
import com.example.myweather.models.Weather

class WeatherRepositoryImplementation(
    private val weatherApiService: WeatherApiService
) : WeatherRepository {
    override suspend fun getWeather(latitude: String, longitude: String): Result<Weather> {
        val result = weatherApiService.getWeather(latitude,longitude)
        return result.fold(
            onSuccess =  {
                Result.success(it.toWeather())
            },
            onFailure = {
                Result.failure<Weather>(it)
            }
        )
    }
}