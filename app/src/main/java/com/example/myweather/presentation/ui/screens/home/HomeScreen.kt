package com.example.myweather.presentation.ui.screens.home

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.location.Location
import android.os.Handler
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
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
import com.example.myweather.data.remote.LocationService
import com.example.myweather.presentation.ui.composables.CurrentInfoCard
import com.example.myweather.presentation.ui.composables.DailyCard
import com.example.myweather.presentation.ui.composables.DetailCard
import com.example.myweather.presentation.ui.composables.HourlyCard
import com.example.myweather.presentation.ui.composables.LocationCard
import com.example.myweather.presentation.ui.theme.urbanist
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.Priority
import kotlin.math.min

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    fusedLocationClient: FusedLocationProviderClient,
    viewModel: HomeViewModel,
    locationService: LocationService
) {
    val state by viewModel.weatherState.collectAsState()
    val context = LocalContext.current
    var isGooglePlayServicesAvailable by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    // Check Google Play Services availability
    LaunchedEffect(Unit) {
        val googleApiAvailability = GoogleApiAvailability.getInstance()
        val resultCode = googleApiAvailability.isGooglePlayServicesAvailable(context)
        if (resultCode == ConnectionResult.SUCCESS) {
            isGooglePlayServicesAvailable = true
        } else {
            errorMessage = "Google Play Services is not available. Location features may not work."
        }
    }

    if (errorMessage != null) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(text = errorMessage!!, color = MaterialTheme.colorScheme.error)
        }
        return
    }

    if (!isGooglePlayServicesAvailable) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
        return
    }

    HomeContent(
        modifier = modifier,
        fusedLocationClient = fusedLocationClient,
        weatherState = state,
        locationConverter = locationService,
        onLocationReceived = { lat, lng ->
            viewModel.updateLocationAndGetWeather(lat, lng)
        }
    )
}

@SuppressLint("DefaultLocale")
@Composable
fun HomeContent(
    modifier: Modifier = Modifier,
    fusedLocationClient: FusedLocationProviderClient,
    weatherState: WeatherState,
    locationConverter: LocationService,
    onLocationReceived: (lat: Double, lng: Double) -> Unit
) {
    val context = LocalContext.current
    var cityName by remember { mutableStateOf("Getting location...") }
    var hasLocationPermission by remember { mutableStateOf(false) }

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        hasLocationPermission = permissions[Manifest.permission.ACCESS_FINE_LOCATION] == true ||
                permissions[Manifest.permission.ACCESS_COARSE_LOCATION] == true

        if (hasLocationPermission) {
            getCurrentLocationAndCity(
                fusedLocationClient = fusedLocationClient,
                locationService = locationConverter,
                onLocationReceived = { city, lat, lng ->
                    cityName = city
                    onLocationReceived(lat, lng)
                },
                onError = { error ->
                    Toast.makeText(context, error, Toast.LENGTH_LONG).show()
                    cityName = "Unable to get location"
                }
            )
        } else {
            Toast.makeText(
                context,
                "Location permissions are required to get your location",
                Toast.LENGTH_LONG
            ).show()
            cityName = "Location permission denied"
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
                locationService = locationConverter,
                onLocationReceived = { city, lat, lng ->
                    cityName = city
                    onLocationReceived(lat, lng)
                },
                onError = { error ->
                    Toast.makeText(context, error, Toast.LENGTH_LONG).show()
                    cityName = "Unable to get location"
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

    val referenceWidth = 360f
    val referenceHeight = 640f
    val iconLargeWidth = 220.21f
    val iconLargeHeight = 200f
    val infoCardLargeWidth = 168f
    val infoCardLargeHeight = 143f
    val iconSmallWidth = 124f
    val iconSmallHeight = 112f
    val infoCardSmallWidth = 168f
    val infoCardSmallHeight = 143f

    val screenHeightDp = with(LocalDensity.current) { LocalConfiguration.current.screenHeightDp.toFloat() }
    val screenWidthDp = with(LocalDensity.current) { LocalConfiguration.current.screenWidthDp.toFloat() }

    val widthScale = screenWidthDp / referenceWidth
    val heightScale = screenHeightDp / referenceHeight
    val scale = min(widthScale, heightScale)

    var isLarge by remember { mutableStateOf(true) }

    LaunchedEffect(scrollState.value) {
        isLarge = !(scrollState.value > screenHeightDp / 9.9)
    }

    val animateIconSize by animateSizeAsState(
        if (isLarge) {
            Size(
                width = iconLargeWidth * scale,
                height = iconLargeHeight * scale
            )
        } else {
            Size(
                width = iconSmallWidth * scale,
                height = iconSmallHeight * scale
            )
        }
    )

    val animateInfoCardSize by animateSizeAsState(
        if (isLarge) {
            Size(
                width = infoCardLargeWidth * scale,
                height = infoCardLargeHeight * scale
            )
        } else {
            Size(
                width = infoCardSmallWidth * scale,
                height = infoCardSmallHeight * scale
            )
        }
    )

    val animateIconPosition by animateIntOffsetAsState(
        if (isLarge) IntOffset(0, 0) else IntOffset(
            (screenWidthDp * 0.05f - (screenWidthDp - iconSmallWidth * scale)).toInt(),
            (screenHeightDp * 0.7f).toInt()
        )
    )

    val animateInfoCardPosition by animateIntOffsetAsState(
        if (isLarge) IntOffset(0, 0) else IntOffset(
            (screenWidthDp - infoCardSmallWidth * scale - screenWidthDp * 0.05f).toInt(),
            (screenHeightDp * 0.3f).toInt()
        )
    )

    val animateAbove by animateIntOffsetAsState(
        if (isLarge) IntOffset(0, 0) else IntOffset(
            0,
            (-(screenHeightDp * 0.6f).toInt())
        )
    )

    val animateContentAbove by animateIntOffsetAsState(
        if (isLarge) IntOffset(0, 0) else IntOffset(
            0,
            (-(screenHeightDp * 0.3f).toInt())
        )
    )

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        containerColor = Color.Transparent,
    ) { innerPadding ->
        Box(
            modifier = modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        listOf(
                            MaterialTheme.colorScheme.primary,
                            MaterialTheme.colorScheme.secondary
                        )
                    )
                )
        ) {
            if (weatherState.isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
            weatherState.weatherInformation?.let { weatherInformation ->
                Column(
                    modifier = Modifier
                        .verticalScroll(scrollState)
                        .statusBarsPadding()
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    LocationCard(
                        modifier = Modifier
                            .padding(top = innerPadding.calculateTopPadding())
                            .clickable {
                                isLarge = !isLarge
                            },
                        locationName = cityName
                    )

                    Image(
                        modifier = Modifier
                            .width(animateIconSize.width.dp)
                            .height(animateIconSize.height.dp)
                            .offset { animateIconPosition }
                            .offset { animateAbove },
                        painter = painterResource(
                            weatherInformation.currentWeather.weatherType.iconRes
                        ),
                        contentDescription = "icon",
                    )
                    CurrentInfoCard(
                        modifier = Modifier
                            .width(animateInfoCardSize.width.dp)
                            .height(animateInfoCardSize.height.dp)
                            .offset { animateInfoCardPosition }
                            .offset { animateAbove },
                        temperature = weatherInformation.currentWeather.temperature,
                        description = weatherInformation.currentWeather.weatherType.weatherDesc,
                        maxTemperature = weatherInformation.dailyWeather[0].maxTemperature,
                        minTemperature = weatherInformation.dailyWeather[0].minTemperature,
                    )
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 24.dp)
                            .padding(horizontal = 12.dp)
                            .offset { animateContentAbove },
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        DetailCard(
                            modifier = Modifier
                                .width((screenWidthDp * 0.3).dp)
                                .weight(1f),
                            value = "${weatherState.weatherInformation.currentWeather.windSpeed} KM/h"
                        )
                        DetailCard(
                            modifier = Modifier
                                .width((screenWidthDp * 0.3).dp)
                                .weight(1f),
                            iconRes = R.drawable.humidity,
                            value = "${weatherState.weatherInformation.currentWeather.relativeHumidity}%",
                            label = "Humidity"
                        )
                        DetailCard(
                            modifier = Modifier
                                .width((screenWidthDp * 0.3).dp)
                                .weight(1f),
                            iconRes = R.drawable.rain,
                            value = "${weatherState.weatherInformation.currentWeather.rain}%",
                            label = "Rain"
                        )
                    }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 12.dp)
                            .padding(top = 6.dp)
                            .offset { animateContentAbove },
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        DetailCard(
                            modifier = Modifier
                                .width((screenWidthDp * 0.3).dp)
                                .weight(1f),
                            iconRes = R.drawable.uv_02,
                            value = "${weatherState.weatherInformation.currentWeather.uvIndex}",
                            label = "UV Index"
                        )
                        DetailCard(
                            modifier = Modifier
                                .width((screenWidthDp * 0.3).dp)
                                .weight(1f),
                            iconRes = R.drawable.arrow_down_05,
                            value = "${weatherState.weatherInformation.currentWeather.surfacePressure} hPa",
                            label = "Pressure"
                        )
                        DetailCard(
                            modifier = Modifier
                                .width((screenWidthDp * 0.3).dp)
                                .weight(1f),
                            iconRes = R.drawable.temperature,
                            value = "${weatherState.weatherInformation.currentWeather.apparentTemperature}°C",
                            label = "Feels like"
                        )
                    }
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 12.dp)
                            .padding(top = 24.dp)
                            .offset { animateContentAbove },
                        text = "Today",
                        style = TextStyle(
                            fontFamily = urbanist,
                            fontWeight = FontWeight.W600,
                            fontSize = 20.sp,
                            lineHeight = 20.sp,
                            letterSpacing = 0.25.sp,
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                    )
                    LazyRow(
                        modifier = Modifier
                            .padding(top = 12.dp)
                            .offset { animateContentAbove },
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
                            .padding(top = 24.dp)
                            .padding(start = 12.dp)
                            .offset { animateContentAbove },
                        text = "Next 7 days",
                        style = TextStyle(
                            fontFamily = urbanist,
                            fontWeight = FontWeight.W600,
                            fontSize = 20.sp,
                            lineHeight = 20.sp,
                            letterSpacing = 0.25.sp,
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                    )
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .offset { animateContentAbove }
                            .padding(top = innerPadding.calculateBottomPadding())
                            .padding(top = 12.dp)
                            .padding(horizontal = 12.dp)
                            .background(
                                MaterialTheme.colorScheme.primaryContainer,
                                shape = RoundedCornerShape(24.dp)
                            )
                            .border(
                                width = 1.dp,
                                color = MaterialTheme.colorScheme.outline,
                                shape = RoundedCornerShape(24.dp)
                            )
                    ) {
                        weatherState.weatherInformation.dailyWeather.forEachIndexed { index, item ->
                            DailyCard(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                dailyWeather = item
                            )
                            if (index != weatherState.weatherInformation.dailyWeather.size.minus(1)) {
                                Spacer(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(1.dp)
                                        .background(MaterialTheme.colorScheme.outline)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

private fun getCurrentLocationAndCity(
    fusedLocationClient: FusedLocationProviderClient,
    locationService: LocationService,
    onLocationReceived: (String, Double, Double) -> Unit,
    onError: (String) -> Unit
) {
    try {
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location: Location? ->
                if (location != null) {
                    locationService.convert(
                        location = location,
                        onSuccess = onLocationReceived,
                        onError = onError
                    )
                } else {
                    requestFreshLocation(
                        fusedLocationClient = fusedLocationClient,
                        locationService = locationService,
                        onLocationReceived = onLocationReceived,
                        onError = onError
                    )
                }
            }
            .addOnFailureListener { exception ->
                requestFreshLocation(
                    fusedLocationClient = fusedLocationClient,
                    locationService = locationService,
                    onLocationReceived = onLocationReceived,
                    onError = onError
                )
            }
    } catch (e: SecurityException) {
        onError("Location permission not granted")
    } catch (e: Exception) {
        onError("Error getting location: ${e.message}")
    }
}

private fun requestFreshLocation(
    fusedLocationClient: FusedLocationProviderClient,
    locationService: LocationService,
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
                    locationService.convert(
                        location = location,
                        onSuccess = onLocationReceived,
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

        Handler(Looper.getMainLooper()).postDelayed({
            fusedLocationClient.removeLocationUpdates(locationCallback)
            onError("Location request timed out. Please try again.")
        }, 30000L)

    } catch (e: SecurityException) {
        onError("Location permission not granted")
    } catch (e: Exception) {
        onError("Error requesting location: ${e.message}")
    }
}