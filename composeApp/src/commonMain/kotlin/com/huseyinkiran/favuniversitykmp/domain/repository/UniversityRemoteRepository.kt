package com.huseyinkiran.favuniversitykmp.domain.repository

import com.huseyinkiran.favuniversitykmp.domain.model.City
import com.huseyinkiran.favuniversitykmp.domain.model.PagingResult

interface UniversityRemoteRepository {

    suspend fun getCities(pageNumber: Int) : PagingResult<City>

}