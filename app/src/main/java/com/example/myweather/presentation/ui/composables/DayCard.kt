package com.example.myweather.presentation.ui.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
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
import com.example.myweather.domain.model.DailyWeather
import com.example.myweather.domain.model.WeatherType
import com.example.myweather.presentation.ui.theme.MyWeatherTheme
import com.example.myweather.presentation.ui.theme.urbanist

@Composable
fun DailyCard(
    modifier: Modifier = Modifier,
    dailyWeather: DailyWeather,
) {
    Box(
        modifier = modifier.fillMaxWidth(),

        ) {
        Text(
            modifier = Modifier
                .align(Alignment.CenterStart)
                .padding(start = 12.dp),
            text = dailyWeather.dayOfWeek,
            style = TextStyle(
                fontFamily = urbanist,
                fontWeight = FontWeight.W400,
                fontSize = 16.sp,
                lineHeight = 16.sp,
                letterSpacing = 0.25.sp,
                color = MaterialTheme.colorScheme.onSecondaryContainer
            )
        )
        Image(
            modifier = Modifier
                .align(Alignment.Center)
                .padding(vertical = 8.dp)
                .height(45.dp),
            painter = painterResource(dailyWeather.weatherType.iconRes),
            contentDescription = "icon"
        )
        TemperatureRangeCard(
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .padding(end = 12.dp),
            highTemperature = dailyWeather.maxTemperature,
            lowTemperature = dailyWeather.minTemperature
        )
    }
}


@Preview
@Composable
private fun Preview() {
    MyWeatherTheme {
        DailyCard(
            dailyWeather = DailyWeather(
                maxTemperature = 25,
                minTemperature = 13,
                dayOfWeek = "Monday",
                weatherType = WeatherType.Fog
            )
        )
    }
}