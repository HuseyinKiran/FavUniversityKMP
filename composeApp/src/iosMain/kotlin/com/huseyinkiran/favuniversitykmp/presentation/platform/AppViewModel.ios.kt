package com.huseyinkiran.favuniversitykmp.presentation.platform

import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import org.koin.compose.koinInject

@Composable
actual inline fun <reified T : ViewModel> appViewModel(): T = koinInject()