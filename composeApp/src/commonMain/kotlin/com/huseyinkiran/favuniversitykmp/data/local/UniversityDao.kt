package com.huseyinkiran.favuniversitykmp.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface UniversityDao {

    @Upsert
    suspend fun upsertUniversity(university: UniversityEntity)

    @Delete
    suspend fun deleteUniversity(university: UniversityEntity)

    @Query("SELECT * FROM favorites ORDER BY addedAt DESC")
    fun getAllFavorites(): Flow<List<UniversityEntity>>

    @Query("SELECT * FROM favorites WHERE id = :universityId")
    suspend fun getUniversityById(universityId: Int): UniversityEntity?

}