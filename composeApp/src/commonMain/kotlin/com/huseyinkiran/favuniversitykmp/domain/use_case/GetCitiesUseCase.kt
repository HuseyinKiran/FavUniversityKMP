package com.huseyinkiran.favuniversitykmp.domain.use_case

import com.huseyinkiran.favuniversitykmp.domain.model.City
import com.huseyinkiran.favuniversitykmp.domain.repository.UniversityRepository

class GetCitiesUseCase(
    private val repository: UniversityRepository
) {

    suspend operator fun invoke(): List<City> {
        val cities = mutableListOf<City>()

        for (page in 1..3) {
            cities += repository.getCities(page).items
        }

        return cities
    }
}