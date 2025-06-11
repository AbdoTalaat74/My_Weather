package com.example.myweather.presentation.ui.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myweather.presentation.ui.theme.MyWeatherTheme
import com.example.myweather.presentation.ui.theme.urbanist

@Composable
fun CurrentInfoCard(
    modifier: Modifier = Modifier,
    temperature: Int,
    description: String,
    maxTemperature: Int,
    minTemperature: Int
) {
    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = "${temperature}°C",
            style = TextStyle(
                fontFamily = urbanist,
                fontWeight = FontWeight.W600,
                fontSize = 64.sp,
                letterSpacing = 0.25.sp,
                lineHeight = 64.sp,
                color = MaterialTheme.colorScheme.onPrimary
            )
        )
        Text(
            modifier = Modifier.padding(bottom = 12.dp),
            text = description,
            style = TextStyle(
                fontFamily = urbanist,
                fontWeight = FontWeight.W500,
                fontSize = 16.sp,
                letterSpacing = 0.25.sp,
                lineHeight = 16.sp,
                color = MaterialTheme.colorScheme.onSecondaryContainer
            )
        )
        Box(
            modifier = Modifier.background(
                color = MaterialTheme.colorScheme.secondaryContainer,
                shape = RoundedCornerShape(100.dp),
            ),
            contentAlignment = Alignment.Center
        ) {
            TemperatureRangeCard(
                modifier = Modifier.padding(horizontal = 24.dp, vertical = 8.dp),
                highTemperature = maxTemperature,
                lowTemperature = minTemperature,
            )
        }
    }
}

@Preview
@Composable
private fun Preview() {
    MyWeatherTheme {
        CurrentInfoCard(
            temperature = 24,
            description = "Partly cloudy",
            maxTemperature = 32,
            minTemperature = 20,
        )
    }
}