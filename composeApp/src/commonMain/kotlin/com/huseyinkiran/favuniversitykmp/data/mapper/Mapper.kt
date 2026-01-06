package com.huseyinkiran.favuniversitykmp.data.mapper

import com.huseyinkiran.favuniversitykmp.data.local.UniversityEntity
import com.huseyinkiran.favuniversitykmp.data.remote.dtos.CityDto
import com.huseyinkiran.favuniversitykmp.data.remote.dtos.UniversityDto
import com.huseyinkiran.favuniversitykmp.domain.model.City
import com.huseyinkiran.favuniversitykmp.domain.model.University

fun CityDto.toCity(): City {
    return City(
        id = id,
        name = name,
        universities = universities.map { it.toUniversity() },
    )
}

fun UniversityDto.toUniversity(): University {
    return University(
        id = id,
        universityType = universityType,
        name = name,
        address = address,
        fax = fax,
        phone = phone,
        rector = rector,
        website = website,
        email = email,
        isFavorite = false
    )
}

fun UniversityEntity.toUniversity(): University {
    return University(
        id = id,
        universityType = universityType,
        name = name,
        address = address,
        fax = fax,
        phone = phone,
        rector = rector,
        website = website,
        email = email,
        isFavorite = true
    )
}

fun University.toEntity(): UniversityEntity {
    return UniversityEntity(
        id = id,
        universityType = universityType,
        name = name,
        fax = fax,
        phone = phone,
        website = website,
        address = address,
        rector = rector,
        email = email
    )
}