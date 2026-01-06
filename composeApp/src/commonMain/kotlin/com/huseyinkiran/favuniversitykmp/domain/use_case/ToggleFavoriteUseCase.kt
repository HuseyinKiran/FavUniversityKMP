package com.huseyinkiran.favuniversitykmp.domain.use_case

import com.huseyinkiran.favuniversitykmp.domain.model.University
import com.huseyinkiran.favuniversitykmp.domain.repository.UniversityRepository

class ToggleFavoriteUseCase(
    private val repository: UniversityRepository
) {

    suspend operator fun invoke(university: University) {
        val existing = repository.getUniversityById(university.id)

        if (existing != null) {
            repository.deleteUniversity(university)
        } else {
            repository.upsertUniversity(university)
        }
    }

}