package com.huseyinkiran.favuniversitykmp.presentation.platform

import androidx.compose.runtime.Composable

@Composable
expect fun rememberMapLauncher(): (address: String) -> Unit

@Composable
expect fun rememberPhoneLauncher(): (phoneNumber: String) -> Unit

@Composable
expect fun rememberEmailLauncher(): (email: String) -> Unit