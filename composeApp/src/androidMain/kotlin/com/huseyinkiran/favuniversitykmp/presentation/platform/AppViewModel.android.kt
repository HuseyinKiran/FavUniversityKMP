package com.huseyinkiran.favuniversitykmp.presentation.platform

import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
actual inline fun <reified T : ViewModel> appViewModel(): T = koinViewModel<T>()