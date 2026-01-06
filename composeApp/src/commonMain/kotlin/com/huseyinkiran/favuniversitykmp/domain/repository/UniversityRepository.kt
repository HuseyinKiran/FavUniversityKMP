package com.huseyinkiran.favuniversitykmp.domain.repository

import com.huseyinkiran.favuniversitykmp.domain.model.City
import com.huseyinkiran.favuniversitykmp.domain.model.PagingResult
import com.huseyinkiran.favuniversitykmp.domain.model.University
import kotlinx.coroutines.flow.Flow

interface UniversityRepository {

    suspend fun upsertUniversity(university: University)

    suspend fun deleteUniversity(university: University)

    fun getAllFavorites() : Flow<List<University>>

    suspend fun getUniversityById(universityId: Int): University?

    suspend fun getCities(pageNumber: Int) : PagingResult<City>

}