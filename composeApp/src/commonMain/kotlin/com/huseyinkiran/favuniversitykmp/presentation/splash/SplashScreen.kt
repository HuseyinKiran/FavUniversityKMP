package com.huseyinkiran.favuniversitykmp.presentation.splash

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.layout.ContentScale
import com.huseyinkiran.favuniversitykmp.presentation.platform.appViewModel
import com.huseyinkiran.favuniversitykmp.presentation.topAppBarColor
import favuniversity.composeapp.generated.resources.Res
import favuniversity.composeapp.generated.resources.bg_favuniversity
import org.jetbrains.compose.resources.painterResource

@Composable
fun SplashScreen(
    onNavigateToHome: () -> Unit
) {

    val viewModel: SplashViewModel = appViewModel()

    var startAnimation by remember { mutableStateOf(false) }

    val alpha by animateFloatAsState(
        targetValue = if (startAnimation) 1f else 0f,
        animationSpec = tween(
            durationMillis = 1000,
            delayMillis = 100
        )
    )

    LaunchedEffect(Unit) {
        startAnimation = true
    }

    LaunchedEffect(Unit) {
        viewModel.navigationEvent.collect { event ->
            when (event) {
                is SplashNavigationEvent.NavigateToHome -> onNavigateToHome()
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(topAppBarColor),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(Res.drawable.bg_favuniversity),
            contentDescription = "Splash Logo",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxSize()
                .alpha(alpha)
        )
    }
}
