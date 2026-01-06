package com.huseyinkiran.favuniversitykmp.di

import com.huseyinkiran.favuniversitykmp.data.local.UniversityDatabase
import com.huseyinkiran.favuniversitykmp.data.local.getFavoritesDatabase
import org.koin.dsl.module

val databaseModule = module {
    single { getFavoritesDatabase() }
    single { get<UniversityDatabase>().universityDao()  }
}