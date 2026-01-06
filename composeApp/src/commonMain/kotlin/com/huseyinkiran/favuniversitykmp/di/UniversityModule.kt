package com.huseyinkiran.favuniversitykmp.di

import com.huseyinkiran.favuniversitykmp.data.remote.KtorUniversityRemoteDataSource
import com.huseyinkiran.favuniversitykmp.data.remote.UniversityRemoteDataSource
import com.huseyinkiran.favuniversitykmp.data.repository.UniversityLocalRepositoryImpl
import com.huseyinkiran.favuniversitykmp.data.repository.UniversityRemoteRepositoryImpl
import com.huseyinkiran.favuniversitykmp.data.repository.UniversityRepositoryImpl
import com.huseyinkiran.favuniversitykmp.domain.repository.UniversityLocalRepository
import com.huseyinkiran.favuniversitykmp.domain.repository.UniversityRemoteRepository
import com.huseyinkiran.favuniversitykmp.domain.repository.UniversityRepository
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val universityModule = module {

    single { createHttpClient() }

    singleOf(::KtorUniversityRemoteDataSource).bind<UniversityRemoteDataSource>()

    singleOf(::UniversityLocalRepositoryImpl).bind<UniversityLocalRepository>()

    singleOf(::UniversityRemoteRepositoryImpl).bind<UniversityRemoteRepository>()

    singleOf(::UniversityRepositoryImpl).bind<UniversityRepository>()

}

