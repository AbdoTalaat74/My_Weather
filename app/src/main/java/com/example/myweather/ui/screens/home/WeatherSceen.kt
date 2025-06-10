package com.example.myweather.ui.screens.home

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.os.Looper
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.core.animateIntOffsetAsState
import androidx.compose.animation.core.animateSizeAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import com.example.myweather.R
import com.example.myweather.ui.composables.CurrentInfoCard
import com.example.myweather.ui.composables.DailyCard
import com.example.myweather.ui.composables.DetailCard
import com.example.myweather.ui.composables.LocationCard
import com.example.myweather.ui.theme.darkBlue
import com.example.myweather.ui.theme.urbanist
import com.giraffe.myweatherapp.presentation.composable.HourlyCard
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.Priority
import org.koin.androidx.compose.koinViewModel
import java.util.Locale

@SuppressLint("DefaultLocale")
@Composable
fun HomeScreen(
    modifier: Modifier,
    fusedLocationClient: FusedLocationProviderClient,
    viewModel: HomeViewModel = koinViewModel()
) {
    val context = LocalContext.current
    var cityName by remember { mutableStateOf("Getting location...") }
    var hasLocationPermission by remember { mutableStateOf(false) }
    var isLoading by remember { mutableStateOf(true) }
    val weatherState by viewModel.weatherState.collectAsState()

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        hasLocationPermission = permissions[Manifest.permission.ACCESS_FINE_LOCATION] == true ||
                permissions[Manifest.permission.ACCESS_COARSE_LOCATION] == true

        if (hasLocationPermission) {
            getCurrentLocationAndCity(
                fusedLocationClient = fusedLocationClient,
                context = context,
                onLocationReceived = { city, lat, lng ->
                    cityName = city
                    isLoading = false
                    viewModel.updateLocationAndGetWeather(lat.toString(), lng.toString())
                },
                onError = { error ->
                    Toast.makeText(context, error, Toast.LENGTH_LONG).show()
                    cityName = "Unable to get location"
                    isLoading = false
                }
            )
        } else {
            Toast.makeText(
                context,
                "Location permissions are required to get your location",
                Toast.LENGTH_LONG
            ).show()
            cityName = "Location permission denied"
            isLoading = false
        }
    }

    LaunchedEffect(Unit) {
        val fineLocationGranted = ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

        val coarseLocationGranted = ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

        hasLocationPermission = fineLocationGranted || coarseLocationGranted

        if (hasLocationPermission) {
            getCurrentLocationAndCity(
                fusedLocationClient = fusedLocationClient,
                context = context,
                onLocationReceived = { city, lat, lng ->
                    cityName = city
                    isLoading = false
                    viewModel.updateLocationAndGetWeather(lat.toString(), lng.toString())
                },
                onError = { error ->
                    Toast.makeText(context, error, Toast.LENGTH_LONG).show()
                    cityName = "Unable to get location"
                    isLoading = false
                }
            )
        } else {
            permissionLauncher.launch(
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
            )
        }
    }

    val scrollState = rememberScrollState()
    val screenHeightPx =
        with(LocalDensity.current) { LocalConfiguration.current.screenHeightDp.dp.toPx() }
    var isLarge by remember { mutableStateOf(true) }
    LaunchedEffect(scrollState.value) {
        isLarge = !(scrollState.value > screenHeightPx / 9.9)
    }
    val animateIconSize by animateSizeAsState(
        if (isLarge) Size(220.21f, 200f) else Size(124f, 112f)
    )
    val animateIconPosition by animateIntOffsetAsState(
        if (isLarge) IntOffset(0, 0) else IntOffset(-168, 88)
    )
    val animateInfoCardPosition by animateIntOffsetAsState(
        if (isLarge) IntOffset(0, 0) else IntOffset(168, -(143))
    )
    val animateAbove by animateIntOffsetAsState(
        if (isLarge) IntOffset(0, 0) else IntOffset(0, -(143))
    )
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                brush = Brush.verticalGradient(
                    listOf(
                        MaterialTheme.colorScheme.primary,
                        MaterialTheme.colorScheme.secondary
                    )
                )
            )
    ) {
        weatherState.weatherInformation?.let { weatherInformation ->


            Column(
                modifier = Modifier
                    .verticalScroll(scrollState)
                    .padding(vertical = 24.dp)
                    .statusBarsPadding()
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                LocationCard(
                    modifier = Modifier.clickable { isLarge = !isLarge },
                    locationName = cityName
                )

                Image(
                    modifier = Modifier
                        .padding(start = 65.dp, end = 65.dp, bottom = 12.dp)
                        .width(animateIconSize.width.dp)
                        .height(animateIconSize.height.dp)
                        .offset {
                            animateIconPosition
                        }
                        .shadow(150.dp, spotColor = MaterialTheme.colorScheme.surfaceBright),
                    painter = painterResource(
                        weatherInformation.currentWeather.weatherType.iconRes
                    ),
                    contentDescription = "icon",
                )
                CurrentInfoCard(
                    modifier = Modifier
                        .offset {
                            animateInfoCardPosition
                        },
                    temperature = weatherInformation.currentWeather.temperature,
                    description =
                        weatherInformation.currentWeather.weatherType.weatherDesc,
                    maxTemperature = weatherInformation.dailyWeather[0].maxTemperature,
                    minTemperature = weatherInformation.dailyWeather[0].minTemperature,
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 12.dp, end = 12.dp, top = 24.dp)
                        .offset { animateAbove },
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    DetailCard(
                        modifier = Modifier.weight(1f),
                        value = "${weatherState.weatherInformation?.currentWeather?.windSpeed} KM/h"
                    )
                    DetailCard(
                        modifier = Modifier.weight(1f),
                        iconRes = R.drawable.humidity,
                        value = "${weatherState.weatherInformation?.currentWeather?.relativeHumidity}%",
                        label = "Humidity"
                    )
                    DetailCard(
                        modifier = Modifier.weight(1f),
                        iconRes = R.drawable.rain,
                        value = "${weatherState.weatherInformation?.currentWeather?.rain}%",
                        label = "Rain"
                    )
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 12.dp, end = 12.dp, top = 6.dp, bottom = 24.dp)
                        .offset { animateAbove },
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    DetailCard(
                        modifier = Modifier.weight(1f),
                        iconRes = R.drawable.uv_02,
                        value = "${weatherState.weatherInformation?.currentWeather?.uvIndex}",
                        label = "UV Index"
                    )
                    DetailCard(
                        modifier = Modifier.weight(1f),
                        iconRes = R.drawable.arrow_down_05,
                        value = "${weatherState.weatherInformation?.currentWeather?.surfacePressure} hPa",
                        label = "Pressure"
                    )
                    DetailCard(
                        modifier = Modifier.weight(1f),
                        iconRes = R.drawable.temperature,
                        value = "${weatherState.weatherInformation?.currentWeather?.apparentTemperature}°C",
                        label = "Feels like"
                    )
                }
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 12.dp, top = 24.dp, bottom = 12.dp)
                        .offset { animateAbove },
                    text = "Today",
                    style = TextStyle(
                        fontFamily = urbanist,
                        fontWeight = FontWeight.W600,
                        fontSize = 20.sp,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                )
                LazyRow(
                    modifier = Modifier
                        .padding(bottom = 24.dp)
                        .offset { animateAbove },
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    contentPadding = PaddingValues(horizontal = 12.dp)
                ) {
                    items(weatherInformation.hourlyWeather) {
                        HourlyCard(
                            hourlyWeather = it
                        )
                    }

                }
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 12.dp, bottom = 12.dp)
                        .offset { animateAbove },
                    text = "Next 7 days",
                    style = TextStyle(
                        fontFamily = urbanist,
                        fontWeight = FontWeight.W600,
                        fontSize = 20.sp,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                )
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp)
                        .border(
                            width = 1.dp,
                            color = MaterialTheme.colorScheme.outline,
                            shape = RoundedCornerShape(24.dp)
                        )
                        .offset { animateAbove }
                        .background(
                            MaterialTheme.colorScheme.primaryContainer,
                            shape = RoundedCornerShape(24.dp)
                        )
                ) {
                    weatherState.weatherInformation?.dailyWeather?.forEachIndexed { index, item ->
                        DailyCard(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp, vertical = 8.dp),
                            dailyWeather = item
                        )
                        if (index != weatherState.weatherInformation?.dailyWeather?.size?.minus(1))
                            Spacer(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(1.dp)
                                    .background(darkBlue.copy(alpha = .08f))
                            )
                    }
                }
            }
        }
    }
}


private fun getCurrentLocationAndCity(
    fusedLocationClient: FusedLocationProviderClient,
    context: android.content.Context,
    onLocationReceived: (String, Double, Double) -> Unit,
    onError: (String) -> Unit
) {
    try {
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location: Location? ->
                if (location != null) {
                    convertLocationToCity(
                        location = location,
                        context = context,
                        onLocationReceived = onLocationReceived,
                        onError = onError
                    )
                } else {
                    requestFreshLocation(
                        fusedLocationClient = fusedLocationClient,
                        context = context,
                        onLocationReceived = onLocationReceived,
                        onError = onError
                    )
                }
            }
            .addOnFailureListener { exception ->
                requestFreshLocation(
                    fusedLocationClient = fusedLocationClient,
                    context = context,
                    onLocationReceived = onLocationReceived,
                    onError = onError
                )
            }
    } catch (e: SecurityException) {
        onError("Location permission not granted")
    }
}

private fun requestFreshLocation(
    fusedLocationClient: FusedLocationProviderClient,
    context: android.content.Context,
    onLocationReceived: (String, Double, Double) -> Unit,
    onError: (String) -> Unit
) {
    try {
        val locationRequest = LocationRequest.Builder(
            Priority.PRIORITY_HIGH_ACCURACY,
            10000L
        ).apply {
            setMinUpdateDistanceMeters(0f)
            setMaxUpdateDelayMillis(15000L)
            setWaitForAccurateLocation(false)
        }.build()

        val locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                super.onLocationResult(locationResult)
                val location = locationResult.lastLocation
                if (location != null) {
                    fusedLocationClient.removeLocationUpdates(this)

                    convertLocationToCity(
                        location = location,
                        context = context,
                        onLocationReceived = onLocationReceived,
                        onError = onError
                    )
                } else {
                    onError("Unable to get current location. Please try again.")
                }
            }
        }
        fusedLocationClient.requestLocationUpdates(
            locationRequest,
            locationCallback,
            Looper.getMainLooper()
        )

        android.os.Handler(Looper.getMainLooper()).postDelayed({
            fusedLocationClient.removeLocationUpdates(locationCallback)
            onError("Location request timed out. Please try again.")
        }, 30000L)

    } catch (e: SecurityException) {
        onError("Location permission not granted")
    } catch (e: Exception) {
        onError("Error requesting location: ${e.message}")
    }
}

private fun convertLocationToCity(
    location: Location,
    context: android.content.Context,
    onLocationReceived: (String, Double, Double) -> Unit,
    onError: (String) -> Unit
) {
    try {
        val geocoder = Geocoder(context, Locale.getDefault())
        val addresses: List<Address>? = geocoder.getFromLocation(
            location.latitude,
            location.longitude,
            1
        )

        if (!addresses.isNullOrEmpty()) {
            val address = addresses[0]
            val cityName =
                address.locality ?: address.subAdminArea ?: address.adminArea ?: "Unknown City"

            onLocationReceived(
                cityName,
                location.latitude,
                location.longitude
            )
        } else {
            onError("Unable to get city name from location")
        }
    } catch (e: Exception) {
        onError("Error converting location to city: ${e.message}")
    }
}