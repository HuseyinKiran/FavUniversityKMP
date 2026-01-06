package com.huseyinkiran.favuniversitykmp.data.repository

import com.huseyinkiran.favuniversitykmp.data.mapper.toUniversity
import com.huseyinkiran.favuniversitykmp.data.remote.UniversityRemoteDataSource
import com.huseyinkiran.favuniversitykmp.domain.model.City
import com.huseyinkiran.favuniversitykmp.domain.model.PagingResult
import com.huseyinkiran.favuniversitykmp.domain.repository.UniversityRemoteRepository

class UniversityRemoteRepositoryImpl(
    private val remoteDataSource: UniversityRemoteDataSource
): UniversityRemoteRepository {

    override suspend fun getCities(pageNumber: Int): PagingResult<City> {
        val response = remoteDataSource.getCities(pageNumber)
        val cities = response.data.map {  cityDto ->
            City(
                id = cityDto.id,
                name = cityDto.name,
                universities = cityDto.universities.map { it.toUniversity() }
            )
        }

        val nextPage = if (response.currentPage < response.totalPage) {
            response.currentPage + 1
        } else {
            null
        }

        return PagingResult(
            items = cities,
            nextPage = nextPage
        )
    }
}