package com.huseyinkiran.favuniversitykmp.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.huseyinkiran.favuniversitykmp.common.ErrorMessages
import com.huseyinkiran.favuniversitykmp.domain.model.University
import com.huseyinkiran.favuniversitykmp.domain.use_case.UniversityUseCases
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeViewModel(
    private val useCases: UniversityUseCases
) : ViewModel() {

    private val _expandedCityIds = MutableStateFlow<Set<Int>>(emptySet())
    val expandedCityIds: StateFlow<Set<Int>> = _expandedCityIds.asStateFlow()

    private val _expandedUniversityIds = MutableStateFlow<Set<Int>>(emptySet())
    val expandedUniversityIds: StateFlow<Set<Int>> = _expandedUniversityIds.asStateFlow()

    private val _baseState = MutableStateFlow(PagingUiState(cities = emptyList()))
    private val baseState: StateFlow<PagingUiState> = _baseState.asStateFlow()

    private val favoriteIdsFlow: Flow<Set<Int>> =
        useCases.getFavoritesUseCase()
            .map { favorites -> favorites.map { it.id }.toSet() }
            .distinctUntilChanged()

    val state: StateFlow<PagingUiState> =
        combine(baseState, favoriteIdsFlow) { current, favIds ->
            if (current.cities.isEmpty()) return@combine current

            val updatedCities = current.cities.map { city ->
                city.copy(
                    universities = city.universities.map { uni ->
                        uni.copy(isFavorite = uni.id in favIds)
                    }
                )
            }

            current.copy(cities = updatedCities)
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = PagingUiState(cities = emptyList(), isLoading = true, errorMessage = null)
        )

    init {
        refresh()
    }

    fun refresh() = viewModelScope.launch {
        _baseState.value = _baseState.value.copy(isLoading = true, errorMessage = null)
        try {
            val cities = withContext(Dispatchers.IO) {
                useCases.getCitiesUseCase()
            }

            _baseState.value = _baseState.value.copy(
                cities = cities,
                isLoading = false,
                errorMessage = null
            )
        } catch (e: Exception) {
            _baseState.value = _baseState.value.copy(
                isLoading = false,
                errorMessage = ErrorMessages.getErrorMessage(e)
            )
        }
    }

    fun toggleFavorite(university: University) = viewModelScope.launch {
        useCases.toggleFavoriteUseCase(university)
    }

    fun onCityExpandToggle(cityId: Int) {
        _expandedCityIds.value = _expandedCityIds.value.toMutableSet().apply {
            if (!add(cityId)) remove(cityId)
        }.toSet()
    }

    fun onUniversityExpandToggle(universityId: Int) {
        _expandedUniversityIds.value = _expandedUniversityIds.value.toMutableSet().apply {
            if (!add(universityId)) remove(universityId)
        }.toSet()
    }
}