package com.huseyinkiran.favuniversitykmp.presentation.favorites

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
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
import com.huseyinkiran.favuniversitykmp.domain.model.University
import com.huseyinkiran.favuniversitykmp.presentation.cards.UniversityCard
import com.huseyinkiran.favuniversitykmp.presentation.platform.appViewModel
import favuniversity.composeapp.generated.resources.Res
import favuniversity.composeapp.generated.resources.empty_favorites_description
import favuniversity.composeapp.generated.resources.empty_favorites_title
import org.jetbrains.compose.resources.stringResource
import org.koin.core.annotation.KoinExperimentalAPI

private val HintTextColor = Color(0xFF9E9E9E)

@OptIn(KoinExperimentalAPI::class)
@Composable
fun FavoritesScreen(
    modifier: Modifier = Modifier
) {
    val viewModel: FavoritesViewModel = appViewModel()
    val state by viewModel.state.collectAsState()
    val expandedUniversityIds by viewModel.expandedUniversityIds.collectAsState()

    com.huseyinkiran.favuniversitykmp.presentation.platform.BackToBackgroundHandler()

    Box(modifier = modifier.fillMaxSize()) {
        if (state.isEmpty) {
            EmptyFavoritesContent()
        } else {
            FavoritesList(
                favorites = state.favorites,
                expandedUniversityIds = expandedUniversityIds,
                onExpandToggle = viewModel::onUniversityExpandToggle,
                onRemoveFavorite = viewModel::removeFavorite
            )
        }
    }
}

@Composable
private fun FavoritesList(
    favorites: List<University>,
    expandedUniversityIds: Set<Int>,
    onExpandToggle: (Int) -> Unit,
    onRemoveFavorite: (University) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        contentPadding = PaddingValues(vertical = 16.dp)
    ) {
        items(
            items = favorites,
            key = { it.id }
        ) { university ->
            UniversityCard(
                university = university,
                isExpanded = university.id in expandedUniversityIds,
                onExpandToggle = { onExpandToggle(university.id) },
                onFavoriteToggle = { },
                showDeleteInsteadOfFavorite = true,
                onDelete = { onRemoveFavorite(university) }
            )
        }
    }
}

@Composable
private fun EmptyFavoritesContent(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = Icons.Default.FavoriteBorder,
                contentDescription = null,
                modifier = Modifier.size(64.dp),
                tint = HintTextColor
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = stringResource(Res.string.empty_favorites_title),
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium,
                color = Color.Black
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = stringResource(Res.string.empty_favorites_description),
                fontSize = 14.sp,
                color = HintTextColor,
                textAlign = TextAlign.Center
            )
        }
    }
}