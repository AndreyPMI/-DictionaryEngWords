package com.andreypmi.core_domain.repository


interface PreferencesDataSource {
    suspend fun <T> setValue(key: String, value: T)
    suspend fun <T> getValue(key: String, defaultValue: T): T
}