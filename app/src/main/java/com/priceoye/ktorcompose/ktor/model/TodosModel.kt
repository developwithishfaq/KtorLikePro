package com.priceoye.ktorcompose.ktor.model

import kotlinx.serialization.Serializable

@Serializable
data class TodosModel(
    val completed: Boolean,
    val id: Int,
    val title: String,
    val userId: Int
)