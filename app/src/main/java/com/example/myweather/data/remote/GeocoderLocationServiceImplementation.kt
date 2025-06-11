package com.example.myweather.data.remote

import android.content.Context
import android.location.Address
import android.location.Geocoder
import android.location.Location
import java.util.Locale

class GeocoderLocationServiceImplementation(private val context: Context) : LocationService {
    override fun convert(
        location: Location,
        onSuccess: (String, Double, Double) -> Unit,
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
                val cityName = address.locality
                    ?: address.subAdminArea
                    ?: address.adminArea
                    ?: "Unknown City"
                onSuccess(cityName, location.latitude, location.longitude)
            } else {
                onError("Unable to get location")
            }
        } catch (e: Exception) {
            onError("An error has occurred while getting location: ${e.message}")
        }
    }
}