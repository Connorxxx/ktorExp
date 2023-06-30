package com.example.models

import kotlinx.serialization.Serializable

@Serializable
data class Customer(
    val id: String,
    val name: String,
    val age: String
)

val customerStorage = mutableListOf<Customer>()