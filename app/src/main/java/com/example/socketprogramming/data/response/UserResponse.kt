package com.example.socketprogramming.data.response

data class UserResponse(
    val email : String,
    val nick : String,
    val sell : List<ProductData>?,
    val buy : List<ProductData>?
)
