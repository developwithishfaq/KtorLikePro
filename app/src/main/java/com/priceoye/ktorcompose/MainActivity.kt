package com.priceoye.ktorcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.priceoye.ktorcompose.ktor.NetworkClient.sendRequest
import com.priceoye.ktorcompose.ktor.NetworkResponse
import com.priceoye.ktorcompose.ktor.RequestTypes
import com.priceoye.ktorcompose.ktor.model.TodoModel
import com.priceoye.ktorcompose.ui.theme.KtorComposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
/*

        val response = sendRequest<TodoModel>(
            "https://jsonplaceholder.typicode.com/todos/", RequestTypes.GET,
            emptyMap()
        )
        when (response) {
            is NetworkResponse.Failure -> {
                response.error?.let {

                }
            }

            is NetworkResponse.Idle -> {

            }

            is NetworkResponse.Loading -> {

            }

            is NetworkResponse.Success -> {
                response.data?.let {

                }
            }
        }
*/


        setContent {
            KtorComposeTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    KtorComposeTheme {
        Greeting("Android")
    }
}