package com.example.myweather.domain.model

import androidx.annotation.DrawableRes
import com.example.myweather.R
import kotlinx.serialization.Serializable

@Serializable
sealed class WeatherType(
    val weatherDesc: String,
    @DrawableRes val iconRes: Int
) {
    object ClearSky : WeatherType(
        weatherDesc = "Clear sky",
        iconRes = R.drawable.clear_sky
    )

    object ClearSkyNight : WeatherType(
        weatherDesc = "Clear sky",
        iconRes = R.drawable.clear_sky_night
    )

    object MainlyClear : WeatherType(
        weatherDesc = "Mainly clear",
        iconRes = R.drawable.mainly_clear
    )

    object MainlyClearNight : WeatherType(
        weatherDesc = "Mainly clear",
        iconRes = R.drawable.mainly_clear_night
    )

    object PartlyCloudy : WeatherType(
        weatherDesc = "Partly cloudy",
        iconRes = R.drawable.partly_cloudy
    )

    object PartlyCloudyNight : WeatherType(
        weatherDesc = "Partly cloudy",
        iconRes = R.drawable.partly_cloudy_night
    )

    object Overcast : WeatherType(
        weatherDesc = "Overcast",
        iconRes = R.drawable.overcast
    )

    object OvercastNight : WeatherType(
        weatherDesc = "Overcast",
        iconRes = R.drawable.overcast_night
    )

    object Fog : WeatherType(
        weatherDesc = "Fog",
        iconRes = R.drawable.fog
    )

    object FogNight : WeatherType(
        weatherDesc = "Fog",
        iconRes = R.drawable.fog_night
    )

    object DepositingRimeFog : WeatherType(
        weatherDesc = "Depositing rime fog",
        iconRes = R.drawable.depositing_rime_fog
    )

    object DepositingRimeFogNight : WeatherType(
        weatherDesc = "Depositing rime fog",
        iconRes = R.drawable.depositing_rime_fog_night
    )

    object DrizzleLight : WeatherType(
        weatherDesc = "Drizzle light",
        iconRes = R.drawable.drizzle_light
    )

    object DrizzleLightNight : WeatherType(
        weatherDesc = "Drizzle light",
        iconRes = R.drawable.drizzle_light_night
    )

    object DrizzleModerate : WeatherType(
        weatherDesc = "Drizzle moderate",
        iconRes = R.drawable.drizzle_moderate
    )

    object DrizzleModerateNight : WeatherType(
        weatherDesc = "Drizzle moderate",
        iconRes = R.drawable.drizzle_moderate_night
    )

    object DrizzleDenseIntensity : WeatherType(
        weatherDesc = "Drizzle intensity",
        iconRes = R.drawable.drizzle_intensity
    )

    object DrizzleDenseIntensityNight : WeatherType(
        weatherDesc = "Drizzle intensity",
        iconRes = R.drawable.drizzle_intensity_night
    )

    object FreezingDrizzleLight : WeatherType(
        weatherDesc = "Freezing drizzle light",
        iconRes = R.drawable.freezing_drizzle_light
    )

    object FreezingDrizzleLightNight : WeatherType(
        weatherDesc = "Freezing drizzle light",
        iconRes = R.drawable.freezing_drizzle_light_night
    )

    object FreezingDrizzleDenseIntensity : WeatherType(
        weatherDesc = "Freezing drizzle dense intensity",
        iconRes = R.drawable.freezing_drizzle_intensity
    )

    object FreezingDrizzleDenseIntensityNight : WeatherType(
        weatherDesc = "Freezing drizzle dense intensity",
        iconRes = R.drawable.freezing_drizzle_intensity_night
    )

    object SlightRain : WeatherType(
        weatherDesc = "Slight rain",
        iconRes = R.drawable.rain_slight
    )

    object SlightRainNight : WeatherType(
        weatherDesc = "Slight rain",
        iconRes = R.drawable.rain_slight_night
    )

    object ModerateRain : WeatherType(
        weatherDesc = "Moderate rain",
        iconRes = R.drawable.rain_moderate
    )

    object ModerateRainNight : WeatherType(
        weatherDesc = "Moderate rain",
        iconRes = R.drawable.rain_moderate_night
    )

    object HeavyIntensityRain : WeatherType(
        weatherDesc = "Intensity rain",
        iconRes = R.drawable.rain_intensity
    )

    object HeavyIntensityRainNight : WeatherType(
        weatherDesc = "Intensity rain",
        iconRes = R.drawable.rain_intensity_night
    )

    object FreezingRainLight : WeatherType(
        weatherDesc = "Freezing rains light",
        iconRes = R.drawable.freezing_light
    )

    object FreezingRainLightNight : WeatherType(
        weatherDesc = "Freezing rains light",
        iconRes = R.drawable.freezing_light_night
    )

    object FreezingRainHeavyIntensity : WeatherType(
        weatherDesc = "Freezing rains heavy intensity",
        iconRes = R.drawable.freezing_heavy
    )

    object FreezingRainHeavyIntensityNight : WeatherType(
        weatherDesc = "Freezing rains heavy intensity",
        iconRes = R.drawable.freezing_heavy_night
    )

    object SlightSnowFall : WeatherType(
        weatherDesc = "Slight snow fall",
        iconRes = R.drawable.snow_fall_light
    )

    object SlightSnowFallNight : WeatherType(
        weatherDesc = "Slight snow fall",
        iconRes = R.drawable.snow_fall_light_night
    )

    object ModerateSnowFall : WeatherType(
        weatherDesc = "Moderate snow fall",
        iconRes = R.drawable.snow_fall_moderate
    )

    object ModerateSnowFallNight : WeatherType(
        weatherDesc = "Moderate snow fall",
        iconRes = R.drawable.snow_fall_moderate_night
    )

    object HeavyIntensitySnowFall : WeatherType(
        weatherDesc = "Heavy intensity snow fall",
        iconRes = R.drawable.snow_fall_intensity
    )

    object HeavyIntensitySnowFallNight : WeatherType(
        weatherDesc = "Heavy intensity fall",
        iconRes = R.drawable.snow_fall_intensity_night
    )

    object SnowGrains : WeatherType(
        weatherDesc = "Snow grains",
        iconRes = R.drawable.snow_grains
    )

    object SnowGrainsNight : WeatherType(
        weatherDesc = "Snow grains",
        iconRes = R.drawable.snow_grains_night
    )

    object SlightRainShowers : WeatherType(
        weatherDesc = "Slight rain showers",
        iconRes = R.drawable.rain_shower_slight
    )

    object SlightRainShowersNight : WeatherType(
        weatherDesc = "Slight rain showers",
        iconRes = R.drawable.rain_shower_slight_night
    )

    object ModerateRainShowers : WeatherType(
        weatherDesc = "Moderate rain showers",
        iconRes = R.drawable.rain_shower_moderate
    )

    object ModerateRainShowersNight : WeatherType(
        weatherDesc = "Moderate rain showers",
        iconRes = R.drawable.rain_shower_moderate_night
    )

    object ViolentRainShowers : WeatherType(
        weatherDesc = "Violent rain showers",
        iconRes = R.drawable.rain_shower_violent
    )

    object ViolentRainShowersNight : WeatherType(
        weatherDesc = "Violent rain showers",
        iconRes = R.drawable.rain_shower_violent_night
    )

    object SlightSnowSowers : WeatherType(
        weatherDesc = "Slight snow showers",
        iconRes = R.drawable.snow_shower_slight
    )

    object SlightSnowSowersNight : WeatherType(
        weatherDesc = "Slight snow showers",
        iconRes = R.drawable.snow_shower_slight_night
    )

    object HeavySnowSowers : WeatherType(
        weatherDesc = "Heavy snow showers",
        iconRes = R.drawable.snow_shower_heavy
    )

    object HeavySnowSowersNight : WeatherType(
        weatherDesc = "Heavy snow showers",
        iconRes = R.drawable.snow_shower_heavy_night
    )

    object SlightThunderstorm : WeatherType(
        weatherDesc = "Slight thunder storm",
        iconRes = R.drawable.thunderstrom_slight_or_moderate
    )

    object SlightThunderstormNight : WeatherType(
        weatherDesc = "Slight thunder storm",
        iconRes = R.drawable.thunderstrom_slight_or_moderate_night
    )

    object ModerateThunderstorm : WeatherType(
        weatherDesc = "Moderate thunder storm",
        iconRes = R.drawable.thunderstrom_slight_or_moderate
    )

    object ModerateThunderstormNight : WeatherType(
        weatherDesc = "Moderate thunder storm",
        iconRes = R.drawable.thunderstrom_slight_or_moderate_night
    )

    object ThunderStormWithSlightHail : WeatherType(
        weatherDesc = "Thunderstorm with slight hail",
        iconRes = R.drawable.thunderstrom_with_slight_hail
    )

    object ThunderStormWithSlightHailNight : WeatherType(
        weatherDesc = "Thunderstorm with slight hail",
        iconRes = R.drawable.thunderstrom_with_slight_hail_night
    )

    object ThunderStormWithHeavyHail : WeatherType(
        weatherDesc = "Thunderstorm with Heavy hail",
        iconRes = R.drawable.thunderstrom_with_heavy_hail
    )

    object ThunderStormWithHeavyHailNight : WeatherType(
        weatherDesc = "Thunderstorm with Heavy hail",
        iconRes = R.drawable.thunderstrom_with_heavy_hail_night
    )

    object UnKnown : WeatherType(
        weatherDesc = "UnKnown",
        iconRes = R.drawable.placeholder_image
    )

}