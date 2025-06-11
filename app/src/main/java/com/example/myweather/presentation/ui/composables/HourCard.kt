package com.example.myweather.presentation.ui.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myweather.domain.model.HourlyWeather
import com.example.myweather.domain.model.WeatherType
import com.example.myweather.presentation.ui.theme.MyWeatherTheme
import com.example.myweather.presentation.ui.theme.urbanist

@Composable
fun HourlyCard(
    modifier: Modifier = Modifier,
    hourlyWeather: HourlyWeather,
    backgroundColor: Color = MaterialTheme.colorScheme.primaryContainer,
    borderColor: Color = MaterialTheme.colorScheme.outline,
) {
    Column(
        modifier = modifier
            .padding(top = 12.dp)
            .border(
                width = 1.dp,
                color = borderColor,
                shape = RoundedCornerShape(20.dp)
            )
            .background(color = backgroundColor, shape = RoundedCornerShape(20.dp)),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Image(
            modifier = Modifier
                .height(58.dp)
                .padding(horizontal = 12.dp)
                .offset(y = (-15).dp),
            painter = painterResource(hourlyWeather.weatherType.iconRes),
            contentDescription = "icon"
        )
        Text(
            modifier = Modifier.padding(vertical = 4.dp, horizontal = 26.dp),
            text = "${hourlyWeather.temperature}°C", style = TextStyle(
                fontFamily = urbanist,
                fontWeight = FontWeight.W500,
                fontSize = 16.sp,
                lineHeight = 16.sp,
                letterSpacing = 0.25.sp,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
        )
        Text(
            modifier = Modifier.padding(bottom = 16.dp, start = 27.5.dp, end = 27.5.dp),
            text = hourlyWeather.time,
            style = TextStyle(
                fontFamily = urbanist,
                fontWeight = FontWeight.W500,
                fontSize = 16.sp,
                lineHeight = 16.sp,
                letterSpacing = 0.25.sp,
                color = MaterialTheme.colorScheme.onSecondaryContainer
            )
        )
    }
}


@Preview
@Composable
private fun Preview() {
    MyWeatherTheme {
        HourlyCard(
            hourlyWeather = HourlyWeather(
                temperature = 15,
                time = "18:11",
                weatherType = WeatherType.OvercastNight
            )
        )
    }
}