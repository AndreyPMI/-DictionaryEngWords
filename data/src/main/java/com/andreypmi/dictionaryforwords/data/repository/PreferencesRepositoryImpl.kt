package com.andreypmi.dictionaryforwords.data.repository

import android.content.Context
import android.content.SharedPreferences
import com.andreypmi.dictionaryforwords.domain.repository.PreferencesRepository

class PreferencesRepositoryImpl(context: Context) : PreferencesRepository {
    private val sharedPrefs: SharedPreferences =
        context.getSharedPreferences("app_preferences", Context.MODE_PRIVATE)

    override suspend fun <T> setValue(key: String, value: T) {
        with(sharedPrefs.edit()) {
            when (value) {
                is Boolean -> putBoolean(key, value)
                is Float -> putFloat(key, value)
                is Int -> putInt(key, value)
                is Long -> putLong(key, value)
                is String -> putString(key, value)
                else -> throw IllegalArgumentException("Unsupported type setValue")
            }
            apply()
        }
    }

    @Suppress("UNCHECKED_CAST")
    override suspend fun <T> getValue(key: String, defaultValue: T): T {
        return with(sharedPrefs) {
            val result = when (defaultValue) {
                is Boolean -> getBoolean(key, defaultValue)
                is Float -> getFloat(key, defaultValue as Float)
                is Int -> getInt(key, defaultValue)
                is Long -> getLong(key, defaultValue)
                is String -> getString(key, defaultValue) ?: defaultValue
                else -> throw IllegalArgumentException("Unsupported type getValue")
            } as T
            result
        }
    }
}
