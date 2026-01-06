package com.huseyinkiran.favuniversitykmp.presentation.navigation

sealed class Screen(val route: String) {

    data object Splash: Screen("splash")
    data object Home: Screen("home")
    data object Search: Screen("search")
    data object Favorites: Screen("favorites")
}