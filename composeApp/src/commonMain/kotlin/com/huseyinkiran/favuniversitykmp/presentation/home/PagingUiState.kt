package com.huseyinkiran.favuniversitykmp.presentation.home

import com.huseyinkiran.favuniversitykmp.domain.model.City

data class PagingUiState(
    val cities: List<City> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
)