package com.example.socketprogramming.data.response

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable
import java.util.*

@Serializable
data class OwnerData(
    @SerializedName("id")
    val id : Int,
    @SerializedName("email")
    val email : Int,
    @SerializedName("nick")
    val nick : String,
    @SerializedName("password")
    val password : String,
    @SerializedName("passwordSalt")
    val passwordSalt : String,
    @SerializedName("money")
    val money : Int,
    @SerializedName("createdAt")
    val createdAt : String,
    @SerializedName("updatedAt")
    val updatedAt : String,
    @SerializedName("deletedAt")
    val deletedAt : String,

    ) : java.io.Serializable

