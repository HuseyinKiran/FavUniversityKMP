package com.huseyinkiran.favuniversitykmp.di

import org.koin.core.context.startKoin

actual class KoinInitializer {
    actual fun init() {
        startKoin {
            modules(universityModule, viewModelModule, databaseModule, useCaseModule)
        }
    }
}