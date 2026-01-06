package com.huseyinkiran.favuniversitykmp.data.remote

import com.huseyinkiran.favuniversitykmp.data.remote.dtos.Response
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter

private const val BASE_URL = "https://favuniversity.huseyinkiran.site/api/provinces"

class KtorUniversityRemoteDataSource(
    private val client: HttpClient
) : UniversityRemoteDataSource {

    override suspend fun getCities(
        page: Int,
        pageSize: Int
    ): Response {
        return client.get(BASE_URL) {
            parameter("page", page)
            parameter("pageSize", pageSize)
        }.body()
    }
}