package com.example.myweather.ui

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.myweather.repo.WeatherRepository
import com.example.myweather.ui.screens.home.HomeScreen
import com.example.myweather.ui.screens.home.HomeViewModel
import com.example.myweather.ui.theme.MyWeatherTheme
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import org.koin.android.ext.android.getKoin

class MainActivity : ComponentActivity() {
    private lateinit var fusedLocationClient: FusedLocationProviderClient


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val weatherRepo: WeatherRepository = getKoin().get()
        val viewModel = HomeViewModel(weatherRepo)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        enableEdgeToEdge()
        setContent {
            Scaffold(
                modifier = Modifier
                    .fillMaxSize(),
                containerColor = Color.Transparent,
            ) { innerPadding ->
                MyWeatherTheme(dynamicColor = false) {
                    HomeScreen(
                        modifier = Modifier.padding(bottom = innerPadding.calculateBottomPadding()),
                        fusedLocationClient = fusedLocationClient,
                        viewModel = viewModel
                    )
                }
            }

        }
    }
}



