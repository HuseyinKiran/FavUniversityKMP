package com.huseyinkiran.favuniversitykmp.common

import platform.Foundation.*

actual fun currentTimeMillis(): Long {
    return (NSDate().timeIntervalSince1970 * 1000.0).toLong()
}