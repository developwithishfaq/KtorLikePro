package com.priceoye.ktorcompose.ktor.model

import kotlinx.serialization.Serializable

@Serializable
data class TodoModel(
    val completed: Boolean,
    val id: Int,
    val title: String,
    val userId: Int
)