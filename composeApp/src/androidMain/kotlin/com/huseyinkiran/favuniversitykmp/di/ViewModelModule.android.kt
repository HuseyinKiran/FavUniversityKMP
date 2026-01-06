package com.huseyinkiran.favuniversitykmp.di

import com.huseyinkiran.favuniversitykmp.presentation.favorites.FavoritesViewModel
import com.huseyinkiran.favuniversitykmp.presentation.home.HomeViewModel
import com.huseyinkiran.favuniversitykmp.presentation.search.SearchViewModel
import com.huseyinkiran.favuniversitykmp.presentation.splash.SplashViewModel
import org.koin.compose.viewmodel.dsl.viewModel
import org.koin.dsl.module

actual val viewModelModule = module {
    viewModel { SplashViewModel() }
    viewModel { HomeViewModel(get()) }
    viewModel { SearchViewModel(get()) }
    viewModel { FavoritesViewModel(get()) }
}