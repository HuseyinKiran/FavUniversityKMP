package com.huseyinkiran.favuniversitykmp.presentation.platform

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import platform.Foundation.*
import platform.UIKit.*

private data class ExternalApp(
    val name: String,
    val urlScheme: String,
    val buildUrl: (query: String) -> String
)

private val mapApps = listOf(
    ExternalApp("Apple Maps", "maps://", { query -> "maps://?q=$query" }),
    ExternalApp("Google Maps", "comgooglemaps://", { query -> "comgooglemaps://?q=$query" }),
    ExternalApp("Yandex Maps", "yandexmaps://", { query -> "yandexmaps://maps.yandex.ru/?text=$query" }),
    ExternalApp("Waze", "waze://", { query -> "waze://?q=$query" })
)

private val emailApps = listOf(
    ExternalApp("Apple Mail", "mailto:", { email -> "mailto:$email" }),
    ExternalApp("Gmail", "googlegmail://", { email -> "googlegmail://co?to=$email" }),
    ExternalApp("Outlook", "ms-outlook://", { email -> "ms-outlook://compose?to=$email" }),
    ExternalApp("Yahoo Mail", "ymail://", { email -> "ymail://mail/compose?to=$email" }),
    ExternalApp("Spark", "readdle-spark://", { email -> "readdle-spark://compose?recipient=$email" })
)

@Composable
actual fun rememberMapLauncher(): (address: String) -> Unit {
    return remember {
        { address: String ->
            runCatching {
                val query = address.percentEncodeForUrlQuery() ?: return@runCatching
                showMapAppChooser(query)
            }.onFailure { it.printStackTrace() }
        }
    }
}

@Composable
actual fun rememberPhoneLauncher(): (phoneNumber: String) -> Unit {
    return remember {
        { phoneNumber: String ->
            runCatching {
                val cleanNumber = phoneNumber.replace(Regex("[^+0-9]"), "")
                if (cleanNumber.isBlank()) return@runCatching

                val primary = "tel:$cleanNumber"
                openUrlWithFallback(primary, null)
            }.onFailure { it.printStackTrace() }
        }
    }
}

@Composable
actual fun rememberEmailLauncher(): (email: String) -> Unit {
    return remember {
        { email: String ->
            runCatching {
                val trimmed = email.trim()
                if (trimmed.isBlank()) return@runCatching

                showEmailAppChooser(trimmed)
            }.onFailure { it.printStackTrace() }
        }
    }
}

private fun showMapAppChooser(encodedAddress: String) {
    val app = UIApplication.sharedApplication

    val availableApps = mapApps.filter { mapApp ->
        val checkUrl = NSURL.URLWithString(mapApp.urlScheme)
        checkUrl != null && app.canOpenURL(checkUrl)
    }

    if (availableApps.size == 1) {
        val selectedApp = availableApps.first()
        val url = NSURL.URLWithString(selectedApp.buildUrl(encodedAddress))
        url?.let { app.openURL(it, emptyMap<Any?, Any?>(), null) }
        return
    }

    if (availableApps.isEmpty()) {
        val webUrl = NSURL.URLWithString("https://maps.apple.com/?q=$encodedAddress")
        webUrl?.let { app.openURL(it, emptyMap<Any?, Any?>(), null) }
        return
    }

    val alertController = UIAlertController.alertControllerWithTitle(
        title = "Harita Uygulaması Seç",
        message = "Hangi uygulama ile haritayı açmak istiyorsunuz?",
        preferredStyle = UIAlertControllerStyleActionSheet
    )

    availableApps.forEach { mapApp ->
        val action = UIAlertAction.actionWithTitle(
            title = mapApp.name,
            style = UIAlertActionStyleDefault
        ) { _ ->
            val url = NSURL.URLWithString(mapApp.buildUrl(encodedAddress))
            url?.let { app.openURL(it, emptyMap<Any?, Any?>(), null) }
        }
        alertController.addAction(action)
    }

    val cancelAction = UIAlertAction.actionWithTitle(
        title = "İptal",
        style = UIAlertActionStyleCancel,
        handler = null
    )
    alertController.addAction(cancelAction)

    val rootViewController = app.keyWindow?.rootViewController
    rootViewController?.presentViewController(alertController, animated = true, completion = null)
}

private fun showEmailAppChooser(email: String) {
    val app = UIApplication.sharedApplication

    val availableApps = emailApps.filter { emailApp ->
        val checkUrl = NSURL.URLWithString(emailApp.urlScheme)
        checkUrl != null && app.canOpenURL(checkUrl)
    }

    if (availableApps.size == 1) {
        val selectedApp = availableApps.first()
        val url = NSURL.URLWithString(selectedApp.buildUrl(email))
        url?.let { app.openURL(it, emptyMap<Any?, Any?>(), null) }
        return
    }

    if (availableApps.isEmpty()) {
        val mailtoUrl = NSURL.URLWithString("mailto:$email")
        mailtoUrl?.let { app.openURL(it, emptyMap<Any?, Any?>(), null) }
        return
    }

    // Birden fazla uygulama varsa seçici göster
    val alertController = UIAlertController.alertControllerWithTitle(
        title = "Email Uygulaması Seç",
        message = "Hangi uygulama ile email göndermek istiyorsunuz?",
        preferredStyle = UIAlertControllerStyleActionSheet
    )

    availableApps.forEach { emailApp ->
        val action = UIAlertAction.actionWithTitle(
            title = emailApp.name,
            style = UIAlertActionStyleDefault
        ) { _ ->
            val url = NSURL.URLWithString(emailApp.buildUrl(email))
            url?.let { app.openURL(it, emptyMap<Any?, Any?>(), null) }
        }
        alertController.addAction(action)
    }

    val cancelAction = UIAlertAction.actionWithTitle(
        title = "İptal",
        style = UIAlertActionStyleCancel,
        handler = null
    )
    alertController.addAction(cancelAction)

    val rootViewController = app.keyWindow?.rootViewController
    rootViewController?.presentViewController(alertController, animated = true, completion = null)
}

private fun String.percentEncodeForUrlQuery(): String? {
    val ns = NSString.create(string = this)
    val allowed = NSCharacterSet.URLQueryAllowedCharacterSet
    return ns.stringByAddingPercentEncodingWithAllowedCharacters(allowed)
}

private fun openUrlWithFallback(primaryUrl: String, fallbackUrl: String?) {
    val app = UIApplication.sharedApplication

    val primary = NSURL.URLWithString(primaryUrl)
    if (primary != null && app.canOpenURL(primary)) {
        app.openURL(primary, emptyMap<Any?, Any?>(), null)
        return
    }

    val fallback = fallbackUrl?.let { NSURL.URLWithString(it) }
    if (fallback != null && app.canOpenURL(fallback)) {
        app.openURL(fallback, emptyMap<Any?, Any?>(), null)
    }
}