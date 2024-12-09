
package com.example.courskotlin.utils

import com.example.courskotlin.R

object PlatformIconProvider {

    // static map in order to associate icons to platforms
    private val platformIcons = mapOf(
        "windows" to R.drawable.ic_windows,
        "pc" to R.drawable.ic_windows,
        "playstation2" to R.drawable.ic_playstation,
        "playstation3" to R.drawable.ic_playstation,
        "playstation4" to R.drawable.ic_playstation,
        "playstation5" to R.drawable.ic_playstation,
        "playstation" to R.drawable.ic_playstation,
        "mac" to R.drawable.ic_mac,
        "macos" to R.drawable.ic_mac,
        "xboxonex" to R.drawable.ic_xbox,
        "xboxones" to R.drawable.ic_xbox,
        "xboxseriesx" to R.drawable.ic_xbox,
        "xbox" to R.drawable.ic_xbox,
        "xbox360" to R.drawable.ic_xbox,
        "nintendoswitch" to R.drawable.ic_nintendo,
        "nintendo" to R.drawable.ic_nintendo,
        "android" to R.drawable.ic_android,
        "linux" to R.drawable.ic_linux
    )

    fun getPlatformIcon(platformSlug: String): Int {
        val cleanedPlatform = platformSlug.lowercase().replace(" ", "").replace("-", "")
        return platformIcons[cleanedPlatform] ?: R.drawable.ic_android
    }
}