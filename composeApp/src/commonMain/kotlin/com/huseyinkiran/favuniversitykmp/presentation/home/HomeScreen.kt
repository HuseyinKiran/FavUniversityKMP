package com.huseyinkiran.favuniversitykmp.presentation.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.huseyinkiran.favuniversitykmp.presentation.cards.CityCard
import com.huseyinkiran.favuniversitykmp.presentation.platform.appViewModel
import com.huseyinkiran.favuniversitykmp.presentation.topAppBarColor
import favuniversity.composeapp.generated.resources.Res
import favuniversity.composeapp.generated.resources.error_occurred
import favuniversity.composeapp.generated.resources.try_again
import org.jetbrains.compose.resources.stringResource
import org.koin.core.annotation.KoinExperimentalAPI

@OptIn(KoinExperimentalAPI::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier
) {
    val viewModel: HomeViewModel = appViewModel()
    val state by viewModel.state.collectAsState()
    val expandedCityIds by viewModel.expandedCityIds.collectAsState()
    val expandedUniversityIds by viewModel.expandedUniversityIds.collectAsState()

    com.huseyinkiran.favuniversitykmp.presentation.platform.BackToBackgroundHandler()

    Box(modifier = modifier.fillMaxSize()) {
        when {
            state.isLoading -> {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center),
                    color = topAppBarColor
                )
            }

            state.errorMessage != null -> {
                ErrorContent(
                    message = state.errorMessage!!,
                    onRetry = viewModel::refresh,
                    modifier = Modifier.align(Alignment.Center)
                )
            }

            else -> {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(top = 12.dp, bottom = 8.dp)
                ) {
                    items(
                        items = state.cities,
                        key = { city -> city.id }
                    ) { city ->
                        CityCard(
                            city = city,
                            universityList = city.universities,
                            isCityExpanded = city.id in expandedCityIds,
                            expandedUniversityIds = expandedUniversityIds,
                            onCityExpandToggle = viewModel::onCityExpandToggle,
                            onUniversityExpandToggle = viewModel::onUniversityExpandToggle,
                            onFavoriteToggle = viewModel::toggleFavorite
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun ErrorContent(
    message: String,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(Res.string.error_occurred),
            fontSize = 18.sp,
            fontWeight = FontWeight.Medium,
            color = Color.Black
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = message,
            fontSize = 14.sp,
            color = Color(0xFF9E9E9E),
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(16.dp))
        TextButton(onClick = onRetry) {
            Text(
                text = stringResource(Res.string.try_again),
                color = topAppBarColor,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}