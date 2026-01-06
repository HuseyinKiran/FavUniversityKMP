package com.huseyinkiran.favuniversitykmp.domain.use_case

import com.huseyinkiran.favuniversitykmp.domain.model.University
import com.huseyinkiran.favuniversitykmp.domain.repository.UniversityRepository

class DeleteUniversityUseCase(
    private val repository: UniversityRepository
) {

    suspend operator fun invoke(university: University) = repository.deleteUniversity(university)

}