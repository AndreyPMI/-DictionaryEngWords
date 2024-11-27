package com.andreypmi.dictionaryforwords.domain.repository


interface PreferencesRepository {
    suspend fun <T> setValue(key: String, value: T)
    suspend fun <T> getValue(key: String, defaultValue: T): T
}