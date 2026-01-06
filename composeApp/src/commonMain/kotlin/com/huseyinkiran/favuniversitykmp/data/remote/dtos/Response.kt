package com.huseyinkiran.favuniversitykmp.data.remote.dtos

import kotlinx.serialization.Serializable

@Serializable
data class Response(
    val currentPage: Int,
    val totalPage: Int,
    val total: Int,
    val pageSize: Int,
    val itemPerPage: Int,
    val data: List<CityDto>
)