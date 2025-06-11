package com.example.myweather.data.remote.dto

import com.example.myweather.data.remote.utils.Utils
import com.example.myweather.domain.model.CurrentWeather
import com.example.myweather.domain.model.DailyWeather
import com.example.myweather.domain.model.HourlyWeather
import com.example.myweather.domain.model.Weather
import com.example.myweather.domain.model.WeatherType
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class WeatherResponse(
    val current: Current,
    val daily: Daily,
    val hourly: Hourly,
    val latitude: Double,
    val longitude: Double,
    val timezone: String,
)

@Serializable
data class Current(
    @SerialName("apparent_temperature") val apparentTemperature: Double,
    val interval: Int,
    @SerialName("is_day") val isDay: Int,
    @SerialName("precipitation_probability") val rain: Int,
    @SerialName("relative_humidity_2m") val relativeHumidity: Int,
    @SerialName("surface_pressure") val surfacePressure: Double,
    @SerialName("temperature_2m") val temperature: Double,
    @SerialName("uv_index") val uvIndex: Double,
    @SerialName("weather_code") val weatherCode: Int,
    @SerialName("wind_speed_10m") val windSpeed: Double
)

@Serializable
data class Daily(
    @SerialName("temperature_2m_max")
    val maxTemperature: List<Double>,
    @SerialName("temperature_2m_min")
    val minTemperature: List<Double>,
    @SerialName("time")
    val date: List<String>,
    @SerialName("weather_code")
    val weatherCode: List<Int>
)

@Serializable
data class Hourly(
    @SerialName("temperature_2m")
    val temperature: List<Double>,
    val time: List<String>,
    @SerialName("weather_code")
    val weatherCode: List<Int>,
    @SerialName("is_day")
    val isDay: List<Int>
)

fun WeatherResponse.toWeather(): Weather {
    return Weather(
        currentWeather = this.current.toCurrentWeather(),
        dailyWeather = this.daily.toDailyWeather(),
        hourlyWeather = this.hourly.toHourlyWeather(),
        latitude = this.latitude,
        longitude = this.longitude,
        timezone = this.timezone
    )
}


fun Current.toCurrentWeather(): CurrentWeather {
    return CurrentWeather(
        apparentTemperature = this.apparentTemperature.toInt(),
        interval = this.interval,
        isDay = this.isDay != 0,
        rain = this.rain,
        relativeHumidity = this.relativeHumidity,
        surfacePressure = this.surfacePressure.toInt(),
        temperature = this.temperature.toInt(),
        uvIndex = this.uvIndex.toInt(),
        weatherType = toWeatherType(this.weatherCode, this.isDay),
        windSpeed = this.windSpeed.toInt()
    )
}

fun Daily.toDailyWeather(): List<DailyWeather> {
    val dailyWeatherList = mutableListOf<DailyWeather>()
    for (day in 0..6) {
        dailyWeatherList.add(
            DailyWeather(
                maxTemperature = this.maxTemperature[day].toInt(),
                minTemperature = this.minTemperature[day].toInt(),
                dayOfWeek = Utils.getWeekDayName(this.date[day]),
                weatherType = toWeatherType(this.weatherCode[day], 1)
            )
        )
    }
    return dailyWeatherList
}

fun Hourly.toHourlyWeather(): List<HourlyWeather> {
    val hourlyWeatherList = mutableListOf<HourlyWeather>()
    for (hour in 0..5) {
        hourlyWeatherList.add(
            HourlyWeather(
                temperature = this.temperature[hour].toInt(),
                time = Utils.convertToTime(this.time[hour]),
                weatherType = toWeatherType(this.weatherCode[hour], this.isDay[hour])
            )
        )
    }
    return hourlyWeatherList
}

fun toWeatherType(weatherCode: Int, isDay: Int): WeatherType {
    return when (weatherCode) {
        0 -> {
            if (isDay == 1) WeatherType.ClearSky else WeatherType.ClearSkyNight
        }

        1 -> {
            if (isDay == 1) WeatherType.MainlyClear else WeatherType.MainlyClearNight
        }

        2 -> {
            if (isDay == 1) WeatherType.PartlyCloudy else WeatherType.PartlyCloudyNight
        }

        3 -> {
            if (isDay == 1) WeatherType.Overcast else WeatherType.OvercastNight
        }

        45 -> {
            if (isDay == 1) WeatherType.Fog else WeatherType.FogNight
        }

        48 -> {
            if (isDay == 1) WeatherType.DepositingRimeFog else WeatherType.DepositingRimeFogNight
        }

        51 -> {
            if (isDay == 1) WeatherType.DrizzleLight else WeatherType.DrizzleLightNight
        }

        53 -> {
            if (isDay == 1) WeatherType.DrizzleModerate else WeatherType.DrizzleModerateNight
        }

        55 -> {
            if (isDay == 1) {
                WeatherType.DrizzleDenseIntensity
            } else {
                WeatherType.DrizzleDenseIntensityNight
            }
        }

        56 -> {
            if (isDay == 1) {
                WeatherType.FreezingDrizzleLight
            } else {
                WeatherType.FreezingDrizzleLightNight
            }
        }

        57 -> {
            if (isDay == 1) {
                WeatherType.FreezingDrizzleDenseIntensity
            } else {
                WeatherType.FreezingDrizzleDenseIntensityNight
            }
        }

        61 -> {
            if (isDay == 1) WeatherType.SlightRain else WeatherType.SlightRainNight
        }

        63 -> {
            if (isDay == 1) WeatherType.ModerateRain else WeatherType.ModerateRainNight
        }

        65 -> {
            if (isDay == 1) WeatherType.HeavyIntensityRain else WeatherType.HeavyIntensityRainNight
        }

        66 -> {
            if (isDay == 1) WeatherType.FreezingRainLight else WeatherType.FreezingRainLightNight
        }

        67 -> {
            if (isDay == 1) {
                WeatherType.FreezingRainHeavyIntensity
            } else {
                WeatherType.FreezingRainHeavyIntensityNight
            }
        }

        71 -> {
            if (isDay == 1) WeatherType.SlightSnowFall else WeatherType.SlightSnowFallNight
        }

        73 -> {
            if (isDay == 1) WeatherType.ModerateSnowFall else WeatherType.ModerateSnowFallNight
        }

        75 -> {
            if (isDay == 1) {
                WeatherType.HeavyIntensitySnowFall
            } else {
                WeatherType.HeavyIntensitySnowFallNight
            }
        }

        80 -> {
            if (isDay == 1) WeatherType.SlightRainShowers else WeatherType.SlightRainShowersNight
        }

        81 -> {
            if (isDay == 1) {
                WeatherType.ModerateRainShowers
            } else {
                WeatherType.ModerateRainShowersNight
            }
        }

        82 -> {
            if (isDay == 1) WeatherType.ViolentRainShowers else WeatherType.ViolentRainShowersNight
        }

        85 -> {
            if (isDay == 1) WeatherType.SlightSnowSowers else WeatherType.SlightSnowSowersNight
        }

        86 -> {
            if (isDay == 1) WeatherType.HeavySnowSowers else WeatherType.HeavySnowSowersNight
        }

        95 -> {
            if (isDay == 1) {
                WeatherType.SlightThunderstorm
            } else {
                WeatherType.SlightThunderstormNight
            }
        }

        96 -> {
            if (isDay == 1) {
                WeatherType.ThunderStormWithSlightHail
            } else {
                WeatherType.ThunderStormWithSlightHailNight
            }
        }

        99 -> {
            if (isDay == 1) {
                WeatherType.ThunderStormWithHeavyHail
            } else {
                WeatherType.ThunderStormWithHeavyHailNight
            }
        }

        else -> WeatherType.UnKnown

    }
}