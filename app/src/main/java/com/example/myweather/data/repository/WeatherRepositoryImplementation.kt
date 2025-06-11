package com.example.myweather.data.repository

import com.example.myweather.data.remote.WeatherApiService
import com.example.myweather.data.remote.dto.toWeather
import com.example.myweather.domain.model.Weather
import com.example.myweather.domain.repository.WeatherRepository

class WeatherRepositoryImplementation(
    private val weatherApiService: WeatherApiService
) : WeatherRepository {
    override suspend fun getWeather(latitude: Double, longitude: Double): Result<Weather> {
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