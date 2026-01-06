package com.huseyinkiran.favuniversitykmp.di

import com.huseyinkiran.favuniversitykmp.domain.use_case.DeleteUniversityUseCase
import com.huseyinkiran.favuniversitykmp.domain.use_case.GetAllUniversitiesUseCase
import com.huseyinkiran.favuniversitykmp.domain.use_case.GetCitiesUseCase
import com.huseyinkiran.favuniversitykmp.domain.use_case.GetFavoritesUseCase
import com.huseyinkiran.favuniversitykmp.domain.use_case.ToggleFavoriteUseCase
import com.huseyinkiran.favuniversitykmp.domain.use_case.UniversityUseCases
import com.huseyinkiran.favuniversitykmp.domain.use_case.UpsertUniversityUseCase
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val useCaseModule = module {
    factoryOf(::UpsertUniversityUseCase)
    factoryOf(::DeleteUniversityUseCase)
    factoryOf(::ToggleFavoriteUseCase)
    factoryOf(::GetFavoritesUseCase)
    factoryOf(::GetCitiesUseCase)
    factoryOf(::GetAllUniversitiesUseCase)

    factory {
        UniversityUseCases(
            deleteUniversityUseCase = get(),
            upsertUniversityUseCase = get(),
            toggleFavoriteUseCase = get(),
            getFavoritesUseCase = get(),
            getCitiesUseCase = get(),
            getAllUniversitiesUseCase = get()
        )
    }
}