package com.example.myweather.ui.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myweather.models.DailyWeather
import com.example.myweather.models.WeatherType
import com.example.myweather.ui.theme.MyWeatherTheme
import com.example.myweather.ui.theme.urbanist

@Composable
fun DailyCard(
    modifier: Modifier = Modifier,
    dailyWeather: DailyWeather,
) {
    Row(
        modifier = modifier
            .height(61.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            modifier = Modifier.weight(1f),
            text = dailyWeather.dayOfWeek,
            style = TextStyle(
                fontFamily = urbanist,
                fontWeight = FontWeight.W400,
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.onSecondaryContainer
            )
        )
        Image(
            modifier = Modifier
                .padding(horizontal = 9.5.dp)
                .weight(1f),
            painter = painterResource(dailyWeather.weatherType.iconRes),
            contentDescription = "icon"
        )
        TemperatureRangeCard(
            modifier = Modifier.weight(103 / 91f),
            highTemperature = dailyWeather.maxTemperature,
            lowTemperature = dailyWeather.minTemperature
        )
    }
}

@Preview
@Composable
private fun Preview() {
    MyWeatherTheme {
        DailyCard(dailyWeather = DailyWeather(
            maxTemperature = 25.5,
            minTemperature = 13.0,
            dayOfWeek = "Monday",
            weatherType = WeatherType.Fog
        ))
    }
}