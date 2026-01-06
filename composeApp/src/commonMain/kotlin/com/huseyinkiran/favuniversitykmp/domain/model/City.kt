package com.huseyinkiran.favuniversitykmp.domain.model

data class City(
    val id: Int,
    val name: String,
    val universities: List<University>,
)