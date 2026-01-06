package com.huseyinkiran.favuniversitykmp.data.remote.dtos

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UniversityDto(
    val id: Int,
    @SerialName("university_type")
    val universityType: String,
    val name: String,
    val phone: String,
    val fax: String,
    val website: String,
    val email: String,
    val address: String,
    val rector: String,
)
