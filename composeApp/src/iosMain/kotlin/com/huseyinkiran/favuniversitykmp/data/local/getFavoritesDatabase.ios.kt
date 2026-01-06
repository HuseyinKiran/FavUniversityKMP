package com.huseyinkiran.favuniversitykmp.data.local

import androidx.room.Room
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import kotlinx.cinterop.ExperimentalForeignApi
import platform.Foundation.NSApplicationSupportDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSUserDomainMask

@OptIn(ExperimentalForeignApi::class)
actual fun getFavoritesDatabase(): UniversityDatabase {
    val fileManager = NSFileManager.defaultManager
    val appSupportUrl = fileManager.URLForDirectory(
        directory = NSApplicationSupportDirectory,
        inDomain = NSUserDomainMask,
        appropriateForURL = null,
        create = true,
        error = null
    ) ?: throw IllegalStateException("Could not get Application Support directory")

    val dbFile = "${appSupportUrl.path}/favorites.db"
    return Room.databaseBuilder<UniversityDatabase>(
        name = dbFile,
        factory = { UniversityDatabaseConstructor.initialize() }
    )
        .setDriver(BundledSQLiteDriver())
        .build()
}