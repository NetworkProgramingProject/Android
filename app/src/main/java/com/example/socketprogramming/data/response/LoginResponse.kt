package com.example.socketprogramming.data.response

data class LoginResponse(
    val status : Int,
    val success : Boolean,
    val message : String,
    val data : String?
)
