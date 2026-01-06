package com.huseyinkiran.favuniversitykmp

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform