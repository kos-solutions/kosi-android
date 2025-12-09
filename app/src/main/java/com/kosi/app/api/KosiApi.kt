package com.kosi.app.api

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.android.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json

object KosiApi {

    private val client = HttpClient(Android) {
        install(ContentNegotiation) {
            json(Json {
                prettyPrint = false
                ignoreUnknownKeys = true
                isLenient = true
            })
        }
    }

    private const val BASE_URL = "https://kosi-backend-production.up.railway.app"

    suspend fun getStory(name: String, age: Int, prompt: String): String {

        val response: StoryResponse = client.post("$BASE_URL/story") {
            header(HttpHeaders.ContentType, "application/json")    // 🔥 OBLIGATORIU
            contentType(ContentType.Application.Json)               // 🔥 OBLIGATORIU
            setBody(
                StoryRequest(
                    child_name = name,
                    age = age,
                    prompt = prompt
                )
            )                                                       // 🔥 ACUM FUNCȚIONEAZĂ
        }.body()

        return response.story
    }
}
