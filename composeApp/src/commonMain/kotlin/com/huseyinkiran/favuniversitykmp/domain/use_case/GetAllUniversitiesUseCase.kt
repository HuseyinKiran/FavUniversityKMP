package com.huseyinkiran.favuniversitykmp.domain.use_case

import com.huseyinkiran.favuniversitykmp.domain.model.University
import com.huseyinkiran.favuniversitykmp.domain.repository.UniversityRepository

class GetAllUniversitiesUseCase(
    private val repository: UniversityRepository
) {

    suspend operator fun invoke(): List<University> {
        val universities = mutableListOf<University>()

        val totalPage = 3

        for (page in 1..totalPage) {
            val cities = repository.getCities(page)
            for (city in cities.items) {
                universities += city.universities
            }
        }

        return universities
    }

}