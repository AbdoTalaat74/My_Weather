package com.example.myweather.presentation.ui.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myweather.R
import com.example.myweather.presentation.ui.theme.MyWeatherTheme
import com.example.myweather.presentation.ui.theme.urbanist

@Composable
fun TemperatureRangeCard(
    modifier: Modifier = Modifier,
    highTemperature: Int,
    lowTemperature: Int,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Image(
                modifier = Modifier
                    .padding(end = 4.dp),
                painter = painterResource(R.drawable.arrow_up),
                contentScale = ContentScale.Crop,
                contentDescription = "arrow up",
                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onPrimaryContainer)
            )
            Text(
                text = "${highTemperature}°C",
                style = TextStyle(
                    fontFamily = urbanist,
                    fontWeight = FontWeight.W500,
                    fontSize = 16.sp,
                    lineHeight = 16.sp,
                    letterSpacing = 0.25.sp,
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                ),
                maxLines = 1
            )
        }

        Spacer(
            modifier = Modifier
                .padding(horizontal = 8.dp)
                .width(1.dp)
                .height(14.dp)
                .background(
                    color = MaterialTheme.colorScheme.outlineVariant,
                    shape = RoundedCornerShape(15.dp)
                )
        )

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Image(
                modifier = Modifier
                    .padding(end = 4.dp),
                painter = painterResource(R.drawable.arrow_down),
                contentScale = ContentScale.Crop,
                contentDescription = "arrow down",
                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onPrimaryContainer)
            )
            Text(
                text = "${lowTemperature}°C",
                style = TextStyle(
                    fontFamily = urbanist,
                    fontWeight = FontWeight.W500,
                    fontSize = 16.sp,
                    lineHeight = 16.sp,
                    letterSpacing = 0.25.sp,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                ),
                maxLines = 1
            )
        }
    }
}


@Preview
@Composable
private fun Preview() {
    MyWeatherTheme {
        TemperatureRangeCard(
            highTemperature = 25,
            lowTemperature = 20,
        )
    }
}