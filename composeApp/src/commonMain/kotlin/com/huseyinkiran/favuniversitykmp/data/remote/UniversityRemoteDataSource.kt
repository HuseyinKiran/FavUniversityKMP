package com.huseyinkiran.favuniversitykmp.data.remote

import com.huseyinkiran.favuniversitykmp.data.remote.dtos.Response

interface UniversityRemoteDataSource {

    suspend fun getCities(page: Int, pageSize: Int = 30): Response

}