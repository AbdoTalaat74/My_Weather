package com.example.myweather.presentation.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.remember
import com.example.myweather.data.remote.GeocoderLocationServiceImplementation
import com.example.myweather.data.remote.LocationService
import com.example.myweather.presentation.ui.screens.home.HomeScreen
import com.example.myweather.presentation.ui.screens.home.HomeViewModel
import com.example.myweather.presentation.ui.theme.MyWeatherTheme
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import org.koin.androidx.compose.get


class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContent {
            val fusedLocationClient: FusedLocationProviderClient = remember {
                LocationServices.getFusedLocationProviderClient(this)
            }
            val locationService: LocationService = GeocoderLocationServiceImplementation(this)
            enableEdgeToEdge()
            MyWeatherTheme(dynamicColor = false) {
                HomeScreen(
                    fusedLocationClient = fusedLocationClient,
                    locationService = locationService,
                    viewModel = get<HomeViewModel>()
                )
            }
        }
    }
}



