package com.huseyinkiran.favuniversitykmp.presentation.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.huseyinkiran.favuniversitykmp.domain.model.University
import com.huseyinkiran.favuniversitykmp.domain.use_case.UniversityUseCases
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class FavoritesViewModel(
    private val useCases: UniversityUseCases
) : ViewModel() {

    private val _expandedUniversityIds = MutableStateFlow<Set<Int>>(emptySet())
    val expandedUniversityIds: StateFlow<Set<Int>> = _expandedUniversityIds

    val state: StateFlow<FavoritesUiState> = useCases.getFavoritesUseCase()
        .map { favorites ->
            FavoritesUiState(favorites = favorites)
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = FavoritesUiState()
        )

    fun removeFavorite(university: University) {
        viewModelScope.launch {
            useCases.deleteUniversityUseCase(university)
        }
    }

    fun onUniversityExpandToggle(universityId: Int) {
        _expandedUniversityIds.value = _expandedUniversityIds.value.toMutableSet().apply {
            if (!add(universityId)) remove(universityId)
        }.toSet()
    }
}