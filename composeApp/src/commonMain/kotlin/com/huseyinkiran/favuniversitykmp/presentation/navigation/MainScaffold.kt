package com.huseyinkiran.favuniversitykmp.presentation.navigation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.displayCutoutPadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import com.huseyinkiran.favuniversitykmp.getPlatform
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.huseyinkiran.favuniversitykmp.presentation.topAppBarColor
import favuniversity.composeapp.generated.resources.Res
import favuniversity.composeapp.generated.resources.title_favorites
import favuniversity.composeapp.generated.resources.title_home
import favuniversity.composeapp.generated.resources.title_search
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScaffold(onRouteChanged: ((String) -> Unit)? = null) {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    LaunchedEffect(currentRoute) {
        currentRoute?.let { route ->
            onRouteChanged?.invoke(route)
        }
    }

    val title = when (currentRoute) {
        Screen.Home.route -> stringResource(Res.string.title_home)
        Screen.Search.route -> stringResource(Res.string.title_search)
        Screen.Favorites.route -> stringResource(Res.string.title_favorites)
        else -> ""
    }

    val isSplashScreen = currentRoute == Screen.Splash.route
    val isIOS = getPlatform().name.contains("iOS", ignoreCase = true)

    val useDarkTheme = if (isIOS) false else isSystemInDarkTheme()
    val colorScheme = if (useDarkTheme) darkColorScheme() else lightColorScheme()

    MaterialTheme(colorScheme = colorScheme) {
        val density = LocalDensity.current
        val imeBottom = WindowInsets.ime.getBottom(density)
        val isKeyboardVisible by rememberUpdatedState(imeBottom > 0)

        Scaffold(
            modifier = Modifier.fillMaxSize(),
            contentWindowInsets = WindowInsets(0.dp),
            topBar = {
                if (!isSplashScreen) {
                    CenterAlignedTopAppBar(
                        title = { Text(title, color = Color.White) },
                        colors = TopAppBarDefaults.topAppBarColors(containerColor = topAppBarColor),
                        windowInsets = WindowInsets.statusBars
                    )
                }
            },
            bottomBar = {
                if (!isSplashScreen) {
                    AnimatedVisibility(
                        visible = !isKeyboardVisible,
                        enter = slideInVertically(initialOffsetY = { it }),
                        exit = slideOutVertically(targetOffsetY = { it })
                    ) {
                        AppBottomBar(navController = navController)
                    }
                }
            }
        ) { innerPadding ->
            BoxWithConstraints(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xFFF5F5F5))
                    .padding(innerPadding)
            ) {
                val isLandscape = maxWidth > maxHeight

                Box(
                    modifier = Modifier.fillMaxSize()
                ) {
                    AppNavHost(
                        navController = navController,
                        modifier = if (isLandscape) {
                            Modifier.displayCutoutPadding().navigationBarsPadding()
                        } else {
                            Modifier
                        }
                    )
                }
            }
        }
    }
}