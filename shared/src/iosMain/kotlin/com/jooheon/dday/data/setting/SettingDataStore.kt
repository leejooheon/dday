package com.jooheon.dday.data.setting

import platform.Foundation.NSUserDefaults

actual class SettingDataStore {
    private val userDefaults = NSUserDefaults.standardUserDefaults()

    actual fun getProfileUrl(): String {
        return userDefaults.stringForKey(PreferenceKey.PROFILE_URL_KEY) ?: ""
    }

    actual fun putProfileUrl(profileUrl: String) {
        userDefaults.setObject(profileUrl, forKey = PreferenceKey.PROFILE_URL_KEY)
    }
}