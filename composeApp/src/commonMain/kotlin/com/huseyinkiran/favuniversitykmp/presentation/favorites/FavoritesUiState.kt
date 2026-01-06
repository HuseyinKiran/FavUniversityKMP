package com.huseyinkiran.favuniversitykmp.presentation.favorites

import com.huseyinkiran.favuniversitykmp.domain.model.University

data class FavoritesUiState(
    val favorites: List<University> = emptyList()
) {
    val isEmpty: Boolean
        get() = favorites.isEmpty()
}