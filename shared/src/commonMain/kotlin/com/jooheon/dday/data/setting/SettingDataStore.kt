package com.jooheon.dday.data.setting

expect class SettingDataStore {
    fun getProfileUrl(): String
    fun putProfileUrl(profileUrl: String)
}