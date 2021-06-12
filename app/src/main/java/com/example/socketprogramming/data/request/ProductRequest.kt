package com.example.socketprogramming.data.request

import com.google.gson.annotations.SerializedName
import okhttp3.MultipartBody
import retrofit2.http.Multipart
import retrofit2.http.Part

data class ProductRequest(
    @Part("title")
    val title : String,
    @Part("desc")
    val desc : String,
    @Part("min_price")
    @SerializedName("min_price")
    val minPrice : Int,
    @Part("img")
    val image : MultipartBody.Part?
)
