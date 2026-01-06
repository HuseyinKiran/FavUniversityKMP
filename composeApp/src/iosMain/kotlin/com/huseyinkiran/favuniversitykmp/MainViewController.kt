package com.huseyinkiran.favuniversitykmp

import androidx.compose.ui.window.ComposeUIViewController
import com.huseyinkiran.favuniversitykmp.di.KoinInitializer
import com.huseyinkiran.favuniversitykmp.presentation.navigation.MainScaffold

fun MainViewController(onRouteChanged: (String) -> Unit): platform.UIKit.UIViewController {
    return ComposeUIViewController(
        configure = {
            KoinInitializer().init()
        }
    ) {
        MainScaffold(onRouteChanged = onRouteChanged)
    }
}