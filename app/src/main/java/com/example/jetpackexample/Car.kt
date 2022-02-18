package com.example.jetpackexample

import androidx.compose.runtime.Composable

data class Car(
    val id: Int,
    val make: String,
    val model: String,
    val year: String,
    val image: Int = 0
)
