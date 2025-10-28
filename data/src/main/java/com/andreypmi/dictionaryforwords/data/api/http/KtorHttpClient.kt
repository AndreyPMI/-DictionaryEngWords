package com.andreypmi.dictionaryforwords.data.api.http

import android.util.Log
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

class KtorHttpClient : HttpClientApi {

    private val client = HttpClient(OkHttp) {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
            })
        }
    }

    override suspend fun getString(url: String): String {
        return client.get(url).body()
    }

    override suspend fun put(url: String, body: Any) {
        client.put(url) {
            contentType(ContentType.Application.Json)
            setBody(body)
        }
    }

    override suspend fun delete(url: String) {
        client.delete(url)
    }
}