package com.huseyinkiran.favuniversitykmp

import android.app.Application
import com.huseyinkiran.favuniversitykmp.di.KoinInitializer

class App: Application() {

    override fun onCreate() {
        super.onCreate()
        KoinInitializer(applicationContext).init()
    }
}