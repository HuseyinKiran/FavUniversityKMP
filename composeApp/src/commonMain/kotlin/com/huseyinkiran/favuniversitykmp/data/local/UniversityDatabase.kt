package com.huseyinkiran.favuniversitykmp.data.local

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [UniversityEntity::class], version = 1)
@ConstructedBy(UniversityDatabaseConstructor::class)
abstract class UniversityDatabase: RoomDatabase() {

    abstract fun universityDao(): UniversityDao

}