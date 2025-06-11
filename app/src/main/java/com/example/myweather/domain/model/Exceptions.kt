package com.example.myweather.domain.model

import com.example.myweather.domain.model.ExceptionsMessages.CLIENT_ERROR
import com.example.myweather.domain.model.ExceptionsMessages.INVALID_API_KEY
import com.example.myweather.domain.model.ExceptionsMessages.INVALID_CITY_NAME
import com.example.myweather.domain.model.ExceptionsMessages.NETWORK_ERROR
import com.example.myweather.domain.model.ExceptionsMessages.SERVER_ERROR
import com.example.myweather.domain.model.ExceptionsMessages.WEATHER_NOT_FOUND


class WeatherNotFondException(message: String = WEATHER_NOT_FOUND) : Exception(message)
class NetworkException(message: String = NETWORK_ERROR) : Exception(message)
class ClientErrorException(message: String = CLIENT_ERROR) : Exception(message)
class InvalidApiKeyException(message: String = INVALID_API_KEY) : Exception(message)
class ServerErrorException(message: String = SERVER_ERROR) : Exception(message)
class InvalidCityNameException(message: String = INVALID_CITY_NAME) : Exception(message)

object ExceptionsMessages {
    const val WEATHER_NOT_FOUND =
        "The weather for this city cannot be determined. Make sure you spell the city name correctly."
    const val NETWORK_ERROR = "Network error: Unable to connect to the server"
    const val CLIENT_ERROR = "Client error"
    const val INVALID_API_KEY = "Invalid or unauthorized API key"
    const val SERVER_ERROR = "Server error"
    const val INVALID_CITY_NAME = "City name is not valid"
}