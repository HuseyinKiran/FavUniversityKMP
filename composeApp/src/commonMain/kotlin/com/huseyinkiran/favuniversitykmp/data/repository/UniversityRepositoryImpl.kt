package com.huseyinkiran.favuniversitykmp.data.repository

import com.huseyinkiran.favuniversitykmp.domain.model.City
import com.huseyinkiran.favuniversitykmp.domain.model.PagingResult
import com.huseyinkiran.favuniversitykmp.domain.model.University
import com.huseyinkiran.favuniversitykmp.domain.repository.UniversityLocalRepository
import com.huseyinkiran.favuniversitykmp.domain.repository.UniversityRemoteRepository
import com.huseyinkiran.favuniversitykmp.domain.repository.UniversityRepository
import kotlinx.coroutines.flow.Flow

class UniversityRepositoryImpl(
    private val localRepository: UniversityLocalRepository,
    private val remoteRepository: UniversityRemoteRepository
): UniversityRepository {
    override suspend fun upsertUniversity(university: University) {
        localRepository.upsertUniversity(university)
    }

    override suspend fun deleteUniversity(university: University) {
        localRepository.deleteUniversity(university)
    }

    override fun getAllFavorites(): Flow<List<University>> {
        return localRepository.getAllFavorites()
    }

    override suspend fun getUniversityById(universityId: Int): University? {
        return localRepository.getUniversityById(universityId)
    }

    override suspend fun getCities(pageNumber: Int): PagingResult<City> {
        return remoteRepository.getCities(pageNumber)
    }
}