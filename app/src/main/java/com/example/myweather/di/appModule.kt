package com.example.myweather.di

import com.example.myweather.api.KtorWeatherApiImplementation
import com.example.myweather.api.WeatherApiService
import com.example.myweather.repo.WeatherRepository
import com.example.myweather.repo.WeatherRepositoryImplementation
import com.example.myweather.ui.screens.home.HomeViewModel
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single<HttpClient> { HttpClient(CIO) }
    single<WeatherApiService> { KtorWeatherApiImplementation(get()) }
    single<WeatherRepository> { WeatherRepositoryImplementation(get()) }
    viewModel { HomeViewModel(get()) }  // Simplified version


}