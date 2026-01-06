package com.huseyinkiran.favuniversitykmp.presentation.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.huseyinkiran.favuniversitykmp.common.ErrorMessages
import com.huseyinkiran.favuniversitykmp.domain.model.University
import com.huseyinkiran.favuniversitykmp.domain.use_case.UniversityUseCases
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@OptIn(FlowPreview::class)
class SearchViewModel(
    private val useCases: UniversityUseCases
) : ViewModel() {

    companion object {
        private const val MIN_QUERY_LENGTH = 2
        private const val DEBOUNCE_DELAY = 300L
    }

    private val _allUniversities = MutableStateFlow<List<University>>(emptyList())

    private val _query = MutableStateFlow("")

    private val _isLoading = MutableStateFlow(false)

    private val _errorMessage = MutableStateFlow<String?>(null)

    private val _expandedUniversityIds = MutableStateFlow<Set<Int>>(emptySet())
    val expandedUniversityIds: StateFlow<Set<Int>> = _expandedUniversityIds

    private val favoriteIdsFlow = useCases.getFavoritesUseCase()
        .map { favorites -> favorites.map { it.id }.toSet() }
        .distinctUntilChanged()

    val state: StateFlow<SearchUiState> = combine(
        _query,
        _allUniversities,
        _isLoading,
        _errorMessage,
        favoriteIdsFlow
    ) { query, universities, isLoading, errorMessage, favoriteIds ->

        val universitiesWithFavorites = universities.map { uni ->
            uni.copy(isFavorite = uni.id in favoriteIds)
        }

        val filtered = if (query.length >= MIN_QUERY_LENGTH) {
            universitiesWithFavorites.filter { university ->
                university.name.contains(query, ignoreCase = true)
            }
        } else {
            emptyList()
        }

        SearchUiState(
            query = query,
            universities = universitiesWithFavorites,
            filteredUniversities = filtered,
            isLoading = isLoading,
            errorMessage = errorMessage
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = SearchUiState()
    )

    init {
        loadAllUniversities()
        observeQueryChanges()
    }

    private fun loadAllUniversities() {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null
            try {
                val universities = withContext(Dispatchers.IO) {
                    useCases.getAllUniversitiesUseCase()
                }
                _allUniversities.value = universities
            } catch (e: Exception) {
                _errorMessage.value = ErrorMessages.getErrorMessage(e)
            } finally {
                _isLoading.value = false
            }
        }
    }

    private fun observeQueryChanges() {
        _query
            .debounce(DEBOUNCE_DELAY)
            .distinctUntilChanged()
            .onEach { query ->
                if (query.length >= MIN_QUERY_LENGTH && _allUniversities.value.isEmpty()) {
                    loadAllUniversities()
                }
            }
            .launchIn(viewModelScope)
    }

    fun onQueryChange(newQuery: String) {
        _query.value = newQuery
    }

    fun onClearQuery() {
        _query.value = ""
    }

    fun toggleFavorite(university: University) {
        viewModelScope.launch {
            useCases.toggleFavoriteUseCase(university)
        }
    }

    fun retry() {
        loadAllUniversities()
    }

    fun onUniversityExpandToggle(universityId: Int) {
        _expandedUniversityIds.value = _expandedUniversityIds.value.toMutableSet().apply {
            if (!add(universityId)) remove(universityId)
        }.toSet()
    }
}