package com.huseyinkiran.favuniversitykmp.data.local

import android.content.Context
import androidx.room.Room
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import org.koin.core.context.GlobalContext

actual fun getFavoritesDatabase(): UniversityDatabase {
    val context: Context = GlobalContext.get().get()

    val dbFile = context.getDatabasePath("favorites.db")
    return Room.databaseBuilder<UniversityDatabase>(
        context = context.applicationContext,
        name = dbFile.absolutePath
    )
        .setDriver(BundledSQLiteDriver())
        .build()
}