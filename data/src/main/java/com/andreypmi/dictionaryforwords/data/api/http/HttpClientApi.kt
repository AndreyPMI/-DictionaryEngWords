package com.andreypmi.dictionaryforwords.data.api.http

interface HttpClientApi {
    suspend fun getString(url: String): String
    suspend fun put(url: String, body: Any)
    suspend fun delete(url: String)
}