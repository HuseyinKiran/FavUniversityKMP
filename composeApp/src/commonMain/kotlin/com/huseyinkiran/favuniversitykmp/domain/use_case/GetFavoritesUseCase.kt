package com.huseyinkiran.favuniversitykmp.domain.use_case

import com.huseyinkiran.favuniversitykmp.domain.repository.UniversityRepository

class GetFavoritesUseCase(
    private val repository: UniversityRepository
) {

    operator fun invoke() = repository.getAllFavorites()

}