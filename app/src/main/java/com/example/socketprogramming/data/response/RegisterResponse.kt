package com.example.socketprogramming.data.response

import com.google.gson.annotations.SerializedName

data class RegisterResponse(
    @SerializedName("status")
    val status : Int,
    @SerializedName("success")
    val success : Boolean,
    @SerializedName("message")
    val message : String,
    @SerializedName("data")
    val data : TokenData?
)
