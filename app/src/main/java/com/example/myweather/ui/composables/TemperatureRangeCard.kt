package com.example.myweather.ui.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myweather.R
import com.example.myweather.ui.theme.MyWeatherTheme
import com.example.myweather.ui.theme.urbanist

@Composable
fun TemperatureRangeCard(
    modifier: Modifier = Modifier,
    highTemperature: Double = 32.0,
    lowTemperature: Double = 20.0,
    fontSize: TextUnit = 14.sp,
    onCardColor: Color = MaterialTheme.colorScheme.onPrimaryContainer,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(
                modifier = Modifier
                    .size(12.dp)
                    .padding(end = 4.dp),
                painter = painterResource(R.drawable.arrow_up),
                contentScale = ContentScale.Crop,
                contentDescription = "arrow up",
                colorFilter = ColorFilter.tint(onCardColor)
            )
            Text(
                text = "${highTemperature.toInt()}°C",
                style = TextStyle(
                    fontFamily = urbanist,
                    fontWeight = FontWeight.W500,
                    fontSize = fontSize,
                    color = onCardColor
                )
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
        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(
                modifier = Modifier
                    .size(12.dp)
                    .padding(end = 4.dp),
                painter = painterResource(R.drawable.arrow_down),
                contentScale = ContentScale.Crop,
                contentDescription = "arrow up",
                colorFilter = ColorFilter.tint(onCardColor)
            )
            Text(
                text = "${lowTemperature.toInt()}°C",
                style = TextStyle(
                    fontFamily = urbanist,
                    fontWeight = FontWeight.W500,
                    fontSize = fontSize,
                    color = onCardColor
                )
            )
        }
    }
}

@Preview
@Composable
private fun Preview() {
    MyWeatherTheme {
        TemperatureRangeCard()
    }
}