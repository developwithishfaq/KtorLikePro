package com.priceoye.ktorcompose.ktor

import android.util.Log
import com.priceoye.ktorcompose.ktor.model.Test
import com.priceoye.ktorcompose.ktor.model.TodoModel
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.headers
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json

sealed class NetworkResponse<T>(
    val data: T? = null,
    val error: String? = null,
) {
    class Idle<T> : NetworkResponse<T>()
    class Loading<T> : NetworkResponse<T>()
    class Success<T>(data: T? = null) : NetworkResponse<T>(data)
    class Failure<T>(error: String? = null) : NetworkResponse<T>(error = error)
}

sealed class RequestTypes {
    data object GET : RequestTypes()
    data class Post(val body: Any? = null) : RequestTypes()
}

object NetworkClient {

    private val httpClient = HttpClient(OkHttp) {
        install(ContentNegotiation) {
            json(
                Json {
                    ignoreUnknownKeys = true
                }
            )
        }
    }


    suspend inline fun <reified T> sendRequest(
        url: String,
        requestTypes: RequestTypes,
        header: Map<String, String>
    ): NetworkResponse<T> {
        return try {
            val response = requestTypes.getHttpClient(url) {
                if (requestTypes is RequestTypes.Post) {
                    it.setBody(requestTypes.body)
                    it.header("Content-Type", "application/json")
                }
                it.headers {
                    header.forEach {
                        append(it.key, it.value)
                    }
                }
            }.body<String>()
            Log.d("cvv", "sendRequest: $response")
            NetworkResponse.Success(Json.decodeFromString(response))
        } catch (e: Exception) {
            NetworkResponse.Failure(e.message)
        }
    }

    suspend fun RequestTypes.getHttpClient(
        url: String,
        callBack: (HttpRequestBuilder) -> Unit
    ): HttpResponse {
        return when (this) {
            RequestTypes.GET -> {
                httpClient.get(url) {
                    callBack.invoke(this)
                }
            }

            is RequestTypes.Post -> {
                httpClient.post(url) {
                    callBack.invoke(this)
                }
            }
        }
    }


    suspend fun hitApi() {
        withContext(Dispatchers.IO) {
            val api = "https://jsonplaceholder.typicode.com/todos/"
            val response = httpClient.get(api).body<TodoModel>()


            httpClient.get(api) {
                headers {
                    append("Authorization", "Bearer wrqwfcver")
                }
            }

            httpClient.post(api) {
                headers {
                    append("Authorization", "Bearer wrqwfcver")
                }
                setBody(Test("Ishfaq"))
            }.body<String>()


        }
    }


}