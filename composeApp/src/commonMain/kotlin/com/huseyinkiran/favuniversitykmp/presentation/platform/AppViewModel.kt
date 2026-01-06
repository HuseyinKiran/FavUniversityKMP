package com.huseyinkiran.favuniversitykmp.presentation.platform

import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel

@Composable
expect inline fun <reified T : ViewModel> appViewModel(): T