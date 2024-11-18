package com.jooheon.dday.data.setting

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking

private val Context.dataStore by preferencesDataStore(name = "settings")
actual class SettingDataStore(private val context: Context) {
    actual fun getProfileUrl(): String {
        val preferencesKey = stringPreferencesKey(PreferenceKey.PROFILE_URL_KEY)
        return runBlocking {
            context.dataStore.data.first()[preferencesKey] ?: ""
        }
    }

    actual fun putProfileUrl(profileUrl: String) {
        val preferencesKey = stringPreferencesKey(PreferenceKey.PROFILE_URL_KEY)

        runBlocking {
            context.dataStore.edit { settings ->
                settings[preferencesKey] = profileUrl
            }
        }
    }

    fun profileUrlFlow(): Flow<String> {
        val preferencesKey = stringPreferencesKey(PreferenceKey.PROFILE_URL_KEY)
        return context.dataStore.data.map {
            it[preferencesKey] ?: ""
        }
    }
}