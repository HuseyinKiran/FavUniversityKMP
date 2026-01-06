package com.huseyinkiran.favuniversitykmp.data.remote.dtos

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CityDto(
    val id: Int,
    @SerialName("province")
    val name: String,
    val universities: List<UniversityDto>
)