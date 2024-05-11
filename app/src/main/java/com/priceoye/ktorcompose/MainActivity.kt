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
import com.priceoye.ktorcompose.ktor.NetworkClient
import com.priceoye.ktorcompose.ktor.NetworkResponse
import com.priceoye.ktorcompose.ktor.RequestType
import com.priceoye.ktorcompose.ktor.model.TodosModel
import com.priceoye.ktorcompose.ui.theme.KtorComposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
/*
        val response = NetworkClient.sendRequest<TodosModel>(
            "https://jsonplaceholder.typicode.com/todos/", RequestType.Get,
            emptyMap()
        )

        when(response){
            is NetworkResponse.Failure -> {

            }
            is NetworkResponse.Idle -> {

            }
            is NetworkResponse.LOADING -> {

            }
            is NetworkResponse.Success -> {
                response.data?.let {

                }
            }
        }*/

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