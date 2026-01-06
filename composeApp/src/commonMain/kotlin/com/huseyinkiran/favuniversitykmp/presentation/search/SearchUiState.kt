package com.huseyinkiran.favuniversitykmp.presentation.search

import com.huseyinkiran.favuniversitykmp.domain.model.University

data class SearchUiState(
    val query: String = "",
    val universities: List<University> = emptyList(),
    val filteredUniversities: List<University> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null
) {
    val isSearchActive: Boolean
        get() = query.length >= 2

    val showEmptyState: Boolean
        get() = isSearchActive && filteredUniversities.isEmpty() && !isLoading && errorMessage == null
}