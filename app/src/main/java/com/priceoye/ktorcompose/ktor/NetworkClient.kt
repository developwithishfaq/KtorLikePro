package com.priceoye.ktorcompose.ktor

import android.util.Log
import com.priceoye.ktorcompose.ktor.model.TodosModel
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
    class LOADING<T> : NetworkResponse<T>()
    class Success<T>(data: T? = null) : NetworkResponse<T>(data)
    class Failure<T>(error: String? = null) : NetworkResponse<T>(error = error)
}

sealed class RequestType {
    data object Get : RequestType()
    data class Post(val body: Any? = null) : RequestType()
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
        requestType: RequestType,
        headers: Map<String, String>
    ) = try {
        val response = requestType.getHttpClient(url) {
            it.headers {
                headers.forEach {
                    append(it.key, it.value)
                }
            }
            if (requestType is RequestType.Post) {
                it.setBody(requestType.body)
                it.header("Content-Type", "application/json")
            }
        }.body<String>()
        Log.d("cvv", "sendRequest: $response")
        NetworkResponse.Success(Json.decodeFromString<T>(response))
    } catch (e: Exception) {
        NetworkResponse.Failure(e.message)
    }


    suspend fun hitApi() {
        withContext(Dispatchers.IO) {
            val url = "https://jsonplaceholder.typicode.com/todos/"
            httpClient.get(url) {
            }.body<List<TodosModel>>()
        }
    }

    suspend fun RequestType.getHttpClient(
        url: String,
        callBack: (HttpRequestBuilder) -> Unit
    ): HttpResponse {
        return when (this) {
            RequestType.Get -> {
                httpClient.get(url) {
                    callBack.invoke(this)
                }
            }

            is RequestType.Post -> {
                httpClient.post(url) {
                    callBack.invoke(this)
                }
            }
        }
    }

    suspend fun hitPostApi() {
        withContext(Dispatchers.IO) {
            val url = "https://jsonplaceholder.typicode.com/todos/"
            httpClient.post(url) {
                headers {

                }
                setBody(TodosModel(false, 1, "", 1))
            }.body<List<TodosModel>>()
        }
    }


}