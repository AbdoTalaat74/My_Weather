package com.example.myweather.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = darkBlue,
    onPrimary = white,
    secondary = blueBlack,
    onSecondary = white,
    primaryContainer = darkBlue.copy(.7f),
    outline = white.copy(alpha = .08f),
    onPrimaryContainer = white.copy(alpha = .87f),
    onSecondaryContainer = white.copy(alpha = .6f),
    outlineVariant = white.copy(alpha = .24f),
    secondaryContainer = white.copy(alpha = .08f),
    surfaceBright = darkShadowColor,
)

private val LightColorScheme = lightColorScheme(
    primary = lightBlue,
    onPrimary = darkBlue,
    secondary = white,
    onSecondary = gray,
    primaryContainer = white.copy(.7f),
    outline = darkBlue.copy(alpha = .08f),
    onPrimaryContainer = darkBlue.copy(alpha = .87f),
    onSecondaryContainer = darkBlue.copy(alpha = .6f),
    outlineVariant = darkBlue.copy(alpha = .24f),
    secondaryContainer = darkBlue.copy(alpha = .08f),
    surfaceBright = lightShadowColor,
)

@Composable
fun MyWeatherTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}