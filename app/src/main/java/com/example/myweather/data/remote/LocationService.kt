package com.example.myweather.data.remote

import android.location.Location

interface LocationService {
    fun convert(
        location: Location,
        onSuccess: (String, Double, Double) -> Unit,
        onError: (String) -> Unit
    )
}