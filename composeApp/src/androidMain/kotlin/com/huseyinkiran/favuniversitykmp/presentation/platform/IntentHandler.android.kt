package com.huseyinkiran.favuniversitykmp.presentation.platform

import android.content.Intent
import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.core.net.toUri

@Composable
actual fun rememberMapLauncher(): (address: String) -> Unit {
    val context = LocalContext.current
    return remember {
        { address: String ->
            try {
                val encodedAddress = Uri.encode(address)
                val geoUri = "geo:0,0?q=$encodedAddress".toUri()
                val mapIntent = Intent(Intent.ACTION_VIEW, geoUri).apply {
                    flags = Intent.FLAG_ACTIVITY_NEW_TASK
                }

                val chooserIntent = Intent.createChooser(mapIntent, "Harita uygulaması seç")
                chooserIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                context.startActivity(chooserIntent)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}

@Composable
actual fun rememberPhoneLauncher(): (phoneNumber: String) -> Unit {
    val context = LocalContext.current
    return remember {
        { phoneNumber: String ->
            try {
                val cleanNumber = phoneNumber.replace(Regex("[^+0-9]"), "")
                val telUri = "tel:$cleanNumber".toUri()
                val dialIntent = Intent(Intent.ACTION_DIAL, telUri).apply {
                    flags = Intent.FLAG_ACTIVITY_NEW_TASK
                }
                context.startActivity(dialIntent)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}

@Composable
actual fun rememberEmailLauncher(): (email: String) -> Unit {
    val context = LocalContext.current
    return remember {
        { email: String ->
            try {
                val emailUri = "mailto:$email".toUri()
                val emailIntent = Intent(Intent.ACTION_SENDTO, emailUri).apply {
                    flags = Intent.FLAG_ACTIVITY_NEW_TASK
                }

                val chooserIntent = Intent.createChooser(emailIntent, "Email uygulaması seç")
                chooserIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                context.startActivity(chooserIntent)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}