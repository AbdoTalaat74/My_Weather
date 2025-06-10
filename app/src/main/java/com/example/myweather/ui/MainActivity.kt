package com.example.myweather.ui

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.example.myweather.repo.WeatherRepository
import com.example.myweather.ui.screens.home.HomeScreen
import com.example.myweather.ui.screens.home.HomeViewModel
import com.example.myweather.ui.theme.MyWeatherTheme
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import org.koin.androidx.compose.getKoin

class MainActivity : ComponentActivity() {
    private lateinit var fusedLocationClient: FusedLocationProviderClient


    @SuppressLint("ViewModelConstructorInComposable")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        enableEdgeToEdge()
        setContent {
            MyWeatherTheme {
                val weatherRepo: WeatherRepository = getKoin().get()
                val viewModel = HomeViewModel(weatherRepo)
                Scaffold { innerPadding ->
                    HomeScreen(
                        modifier = Modifier.padding(innerPadding),
                        fusedLocationClient = fusedLocationClient,
                        viewModel = viewModel
                    )

                }
            }
        }
    }


}