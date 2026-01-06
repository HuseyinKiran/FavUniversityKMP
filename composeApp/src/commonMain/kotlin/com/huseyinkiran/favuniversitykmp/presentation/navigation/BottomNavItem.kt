package com.huseyinkiran.favuniversitykmp.presentation.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.vector.ImageVector
import favuniversity.composeapp.generated.resources.Res
import favuniversity.composeapp.generated.resources.favorites
import favuniversity.composeapp.generated.resources.search
import favuniversity.composeapp.generated.resources.universities
import org.jetbrains.compose.resources.StringResource

data class BottomNavItem(
    val screen: Screen,
    val labelRes: StringResource,
    val icon: ImageVector
)

val bottomNavItems = listOf(
    BottomNavItem(
        screen = Screen.Home,
        labelRes = Res.string.universities,
        icon = Icons.Default.Home
    ),
    BottomNavItem(
        screen = Screen.Search,
        labelRes = Res.string.search,
        icon = Icons.Default.Search
    ),
    BottomNavItem(
        screen = Screen.Favorites,
        labelRes = Res.string.favorites,
        icon = Icons.Default.Favorite
    )
)