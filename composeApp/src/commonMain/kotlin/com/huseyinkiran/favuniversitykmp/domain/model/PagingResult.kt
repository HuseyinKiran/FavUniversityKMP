package com.huseyinkiran.favuniversitykmp.domain.model

data class PagingResult<T>(
    val items: List<T>,
    val nextPage: Int?,
)