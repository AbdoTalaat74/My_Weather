package com.example.myweather.models

class WeatherTypeTC {
    fun fromWeatherType(weatherType: WeatherType): Int {
        return when (weatherType) {
            is WeatherType.ClearSky -> 0
            is WeatherType.ClearSkyNight -> 0
            is WeatherType.MainlyClear -> 1
            is WeatherType.MainlyClearNight -> 1
            is WeatherType.PartlyCloudy -> 2
            is WeatherType.PartlyCloudyNight -> 2
            is WeatherType.Overcast -> 3
            is WeatherType.OvercastNight -> 3
            is WeatherType.Fog -> 45
            is WeatherType.FogNight -> 45
            is WeatherType.DepositingRimeFog -> 48
            is WeatherType.DepositingRimeFogNight -> 48
            is WeatherType.DrizzleLight -> 51
            is WeatherType.DrizzleLightNight -> 51
            is WeatherType.DrizzleModerate -> 53
            is WeatherType.DrizzleModerateNight -> 53
            is WeatherType.DrizzleDenseIntensity -> 55
            is WeatherType.DrizzleDenseIntensityNight -> 55
            is WeatherType.FreezingDrizzleLight -> 56
            is WeatherType.FreezingDrizzleLightNight -> 56
            is WeatherType.FreezingDrizzleDenseIntensity -> 57
            is WeatherType.FreezingDrizzleDenseIntensityNight -> 57
            is WeatherType.SlightRain -> 61
            is WeatherType.SlightRainNight -> 61
            is WeatherType.ModerateRain -> 63
            is WeatherType.ModerateRainNight -> 63
            is WeatherType.HeavyIntensityRain -> 65
            is WeatherType.HeavyIntensityRainNight -> 65
            is WeatherType.FreezingRainLight -> 66
            is WeatherType.FreezingRainLightNight -> 66
            is WeatherType.FreezingRainHeavyIntensity -> 67
            is WeatherType.FreezingRainHeavyIntensityNight -> 67
            is WeatherType.SlightSnowFall -> 71
            is WeatherType.SlightSnowFallNight -> 71
            is WeatherType.ModerateSnowFall -> 73
            is WeatherType.ModerateSnowFallNight -> 73
            is WeatherType.HeavyIntensitySnowFall -> 75
            is WeatherType.HeavyIntensitySnowFallNight -> 75
            is WeatherType.SlightRainShowers -> 80
            is WeatherType.SlightRainShowersNight -> 80
            is WeatherType.ModerateRainShowers -> 81
            is WeatherType.ModerateRainShowersNight -> 81
            is WeatherType.ViolentRainShowers -> 82
            is WeatherType.ViolentRainShowersNight -> 82
            is WeatherType.SlightSnowSowers -> 85
            is WeatherType.SlightSnowSowersNight -> 85
            is WeatherType.HeavySnowSowers -> 86
            is WeatherType.HeavySnowSowersNight -> 86
            is WeatherType.SlightThunderstorm -> 95
            is WeatherType.SlightThunderstormNight -> 95
            is WeatherType.ThunderStormWithSlightHail -> 96
            is WeatherType.ThunderStormWithSlightHailNight -> 96
            is WeatherType.ThunderStormWithHeavyHail -> 99
            is WeatherType.ThunderStormWithHeavyHailNight -> 99
            else -> -1
        }
    }


}