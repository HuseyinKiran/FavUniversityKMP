package com.huseyinkiran.favuniversitykmp.data.repository

import com.huseyinkiran.favuniversitykmp.data.local.UniversityDao
import com.huseyinkiran.favuniversitykmp.data.mapper.toEntity
import com.huseyinkiran.favuniversitykmp.data.mapper.toUniversity
import com.huseyinkiran.favuniversitykmp.domain.model.University
import com.huseyinkiran.favuniversitykmp.domain.repository.UniversityLocalRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UniversityLocalRepositoryImpl(
    private val dao: UniversityDao
): UniversityLocalRepository {

    override suspend fun upsertUniversity(university: University) {
        dao.upsertUniversity(university.toEntity())
    }

    override suspend fun deleteUniversity(university: University) {
        dao.deleteUniversity(university.toEntity())
    }

    override fun getAllFavorites(): Flow<List<University>> {
        return dao.getAllFavorites().map { universityList ->
            universityList.map { it.toUniversity() }
        }
    }

    override suspend fun getUniversityById(universityId: Int): University? {
        return dao.getUniversityById(universityId)?.toUniversity()
    }
}