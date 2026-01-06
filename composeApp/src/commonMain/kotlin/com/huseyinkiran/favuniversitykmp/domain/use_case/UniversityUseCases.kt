package com.huseyinkiran.favuniversitykmp.domain.use_case

data class UniversityUseCases(
    val deleteUniversityUseCase: DeleteUniversityUseCase,
    val upsertUniversityUseCase: UpsertUniversityUseCase,
    val toggleFavoriteUseCase: ToggleFavoriteUseCase,
    val getFavoritesUseCase: GetFavoritesUseCase,
    val getAllUniversitiesUseCase: GetAllUniversitiesUseCase,
    val getCitiesUseCase: GetCitiesUseCase
)
