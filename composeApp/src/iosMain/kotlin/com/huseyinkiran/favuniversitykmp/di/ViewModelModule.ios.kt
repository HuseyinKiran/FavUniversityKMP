package com.huseyinkiran.favuniversitykmp.di

import com.huseyinkiran.favuniversitykmp.presentation.favorites.FavoritesViewModel
import com.huseyinkiran.favuniversitykmp.presentation.home.HomeViewModel
import com.huseyinkiran.favuniversitykmp.presentation.search.SearchViewModel
import com.huseyinkiran.favuniversitykmp.presentation.splash.SplashViewModel
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

actual val viewModelModule = module {
    singleOf(::SplashViewModel)
    singleOf(::HomeViewModel)
    singleOf(::SearchViewModel)
    singleOf(::FavoritesViewModel)
}