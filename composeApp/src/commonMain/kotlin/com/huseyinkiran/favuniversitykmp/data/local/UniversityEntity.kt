package com.huseyinkiran.favuniversitykmp.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.huseyinkiran.favuniversitykmp.common.currentTimeMillis

@Entity(tableName = "favorites")
data class UniversityEntity(
    @PrimaryKey val id: Int,
    val universityType: String,
    val name: String,
    val phone: String,
    val fax: String,
    val website: String,
    val email: String,
    val address: String,
    val rector: String,
    val addedAt: Long = currentTimeMillis()
)