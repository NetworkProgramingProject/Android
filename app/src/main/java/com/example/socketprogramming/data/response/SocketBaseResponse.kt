package com.example.socketprogramming.data.response

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * 소켓 프로그래밍 기본 데이터 구조
 */
@Serializable
data class SocketBaseResponse<T>(

    @SerialName("data")
    @SerializedName("data")
    val result: T?,

    @SerializedName("message")
    @SerialName("message")
    val message: String,

    @SerializedName("status")
    @SerialName("status")
    val statusCode: Int,

    @SerializedName("success")
    @SerialName("success")
    val success : Boolean
)
