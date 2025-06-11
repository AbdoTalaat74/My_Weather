package com.example.myweather.data.remote

import com.example.myweather.data.remote.dto.WeatherResponse
import com.example.myweather.domain.model.ClientErrorException
import com.example.myweather.domain.model.InvalidApiKeyException
import com.example.myweather.domain.model.NetworkException
import com.example.myweather.domain.model.ServerErrorException
import com.example.myweather.domain.model.WeatherNotFondException
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.serialization.json.Json

class KtorWeatherApiImplementation(private val client: HttpClient) : WeatherApiService {
    private val json = Json { ignoreUnknownKeys = true }
    override suspend fun getWeather(latitude: Double, longitude: Double): Result<WeatherResponse> {
        return try {
            val response = client.get(BASE_URL) {
                parameter(LATITUDE_QUERY, latitude.toString())
                parameter(LONGITUDE_QUERY, longitude.toString())
                parameter(DAILY_QUERY, DAILY_PARAMS)
                parameter(HOURLY_QUERY, HOURLY_PARAMS)
                parameter(CURRENT_QUERY, CURRENT_PARAMS)
                parameter(FORECAST_HOURS_QUERY, FORECAST_HOURS)


            }
            when (response.status) {
                HttpStatusCode.OK -> {
                    val weatherDto = json.decodeFromString<WeatherResponse>(response.bodyAsText())
                    Result.success(weatherDto)
                }

                HttpStatusCode.BadRequest -> Result.failure(NetworkException())
                HttpStatusCode.Unauthorized -> Result.failure(InvalidApiKeyException())
                HttpStatusCode.NotFound -> Result.failure(WeatherNotFondException())
                else -> {
                    when (response.status.value) {
                        in 400..499 -> Result.failure(ClientErrorException())
                        in 500..599 -> Result.failure(ServerErrorException())
                        else -> Result.failure(Exception("$UNEXPECTED_ERROR ${response.status.value}"))
                    }
                }
            }

        } catch (e: Exception) {
            Result.failure(e)
        }

    }

    companion object {
        const val BASE_URL = "https://api.open-meteo.com/v1/forecast"
        const val LATITUDE_QUERY = "latitude"
        const val LONGITUDE_QUERY = "longitude"
        const val DAILY_QUERY = "daily"
        const val HOURLY_QUERY = "hourly"
        const val CURRENT_QUERY = "current"
        const val FORECAST_HOURS_QUERY = "forecast_hours"
        const val DAILY_PARAMS = "weather_code,temperature_2m_max,temperature_2m_min"
        const val HOURLY_PARAMS = "temperature_2m,weather_code,is_day,uv_index"
        const val CURRENT_PARAMS = "temperature_2m,temperature_2m_max,temperature_2m_min,uv_index,relative_humidity_2m,precipitation_probability,weather_code,wind_speed_10m,apparent_temperature,is_day,surface_pressure"
        const val FORECAST_HOURS = 6
        const val UNEXPECTED_ERROR = "Unexpected HTTP error:"
    }

}