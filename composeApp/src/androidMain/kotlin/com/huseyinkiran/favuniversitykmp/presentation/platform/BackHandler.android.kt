package com.huseyinkiran.favuniversitykmp.presentation.platform

import android.app.Activity
import android.content.ContextWrapper
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

@Composable
actual fun BackToBackgroundHandler(enabled: Boolean) {
    val activity = LocalContext.current.findActivity()
    androidx.activity.compose.BackHandler(enabled = enabled) {
        activity?.moveTaskToBack(true)
    }
}

private fun android.content.Context.findActivity(): Activity? {
    var context = this
    while (context is ContextWrapper) {
        if (context is Activity) return context
        context = context.baseContext
    }
    return null
}