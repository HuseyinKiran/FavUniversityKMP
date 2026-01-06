package com.huseyinkiran.favuniversitykmp

import android.graphics.Color
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.ui.graphics.toArgb
import com.huseyinkiran.favuniversitykmp.presentation.navigation.MainScaffold
import com.huseyinkiran.favuniversitykmp.presentation.topAppBarColor

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.dark(topAppBarColor.toArgb()),
            navigationBarStyle = SystemBarStyle.light(
                scrim = Color.WHITE,
                darkScrim = Color.WHITE
            )
        )

        setContent {
            MainScaffold()
        }
    }
}