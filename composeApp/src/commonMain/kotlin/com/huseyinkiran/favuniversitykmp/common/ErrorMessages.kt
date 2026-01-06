package com.huseyinkiran.favuniversitykmp.common

import io.ktor.client.network.sockets.ConnectTimeoutException
import io.ktor.client.network.sockets.SocketTimeoutException
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.ServerResponseException
import kotlinx.io.IOException

object ErrorMessages {
    const val NETWORK_ERROR = "İnternet bağlantınızı kontrol edin"
    const val TIMEOUT_ERROR = "Bağlantı zaman aşımına uğradı. Lütfen tekrar deneyin"
    const val SERVER_ERROR = "Sunucuya bağlanılamadı. Lütfen daha sonra tekrar deneyin"
    const val UNKNOWN_ERROR = "Bir hata oluştu. Lütfen tekrar deneyin"
    const val NOT_FOUND_ERROR = "İstenen veri bulunamadı"
    const val BAD_REQUEST_ERROR = "Geçersiz istek. Lütfen daha sonra tekrar deneyin"

    fun getErrorMessage(throwable: Throwable): String {
        return when (throwable) {
            // Network bağlantı hataları
            is ConnectTimeoutException,
            is SocketTimeoutException -> TIMEOUT_ERROR

            // IOException (genel network hataları)
            is IOException -> NETWORK_ERROR

            // HTTP 4xx hataları (client errors)
            is ClientRequestException -> {
                when (throwable.response.status.value) {
                    404 -> NOT_FOUND_ERROR
                    400 -> BAD_REQUEST_ERROR
                    else -> "İstek başarısız oldu (${throwable.response.status.value})"
                }
            }

            // HTTP 5xx hataları (server errors)
            is ServerResponseException -> SERVER_ERROR

            else -> UNKNOWN_ERROR
        }
    }
}
