package com.andreypmi.dictionaryforwords.data.repository

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.andreypmi.core_domain.repository.PreferencesDataSource
import javax.inject.Inject
import androidx.core.content.edit
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


class PreferencesDataSourceImpl @Inject constructor(context: Context) :
    PreferencesDataSource {
    private val sharedPrefs: SharedPreferences =
        context.getSharedPreferences("app_preferences", Context.MODE_PRIVATE)

    override suspend fun <T> setValue(key: String, value: T) {
        withContext(Dispatchers.IO) {
            sharedPrefs.edit().apply {
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
    }

    @Suppress("UNCHECKED_CAST")
    override suspend fun <T> getValue(key: String, defaultValue: T): T {
        return withContext(Dispatchers.IO) {
            val result = when (defaultValue) {
                is Boolean -> sharedPrefs.getBoolean(key, defaultValue)
                is Float -> sharedPrefs.getFloat(key, defaultValue as Float)
                is Int -> sharedPrefs.getInt(key, defaultValue)
                is Long -> sharedPrefs.getLong(key, defaultValue)
                is String -> sharedPrefs.getString(key, defaultValue as String?) ?: defaultValue
                else -> throw IllegalArgumentException("Unsupported type getValue")
            } as T
            result
        }
    }
}