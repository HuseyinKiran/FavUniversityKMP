package com.huseyinkiran.favuniversitykmp.domain.use_case

import com.huseyinkiran.favuniversitykmp.domain.model.University
import com.huseyinkiran.favuniversitykmp.domain.repository.UniversityRepository

class UpsertUniversityUseCase(
    private val repository: UniversityRepository
) {

    suspend operator fun invoke(university: University) = repository.upsertUniversity(university)

}