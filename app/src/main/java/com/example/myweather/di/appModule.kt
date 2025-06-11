package com.example.myweather.di

import android.app.Activity
import android.content.Context
import com.example.myweather.data.remote.GeocoderLocationServiceImplementation
import com.example.myweather.data.remote.KtorWeatherApiImplementation
import com.example.myweather.data.remote.LocationService
import com.example.myweather.data.remote.WeatherApiService
import com.example.myweather.domain.repository.WeatherRepository
import com.example.myweather.data.repository.WeatherRepositoryImplementation
import com.example.myweather.presentation.ui.screens.home.HomeViewModel
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val appModule = module {
    single<HttpClient> { HttpClient(CIO) }
    single<WeatherApiService> { KtorWeatherApiImplementation(get()) }
    single<WeatherRepository> { WeatherRepositoryImplementation(get()) }
    single { HomeViewModel(get()) }

    single<Context> { androidContext() as Activity }
    single<LocationService> { GeocoderLocationServiceImplementation(get()) }
}