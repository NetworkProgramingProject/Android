package com.example.socketprogramming.data.response

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable
import java.util.*

@Serializable
data class AuctionData(
    @SerializedName("id")
    val id : Int,
    @SerializedName("bid")
    val bid : Int,
    @SerializedName("msg")
    val msg : String?,
    @SerializedName("createdAt")
    val createAt : String,
    @SerializedName("updatedAt")
    val updatedAt : String,
    @SerializedName("deletedAt")
    val deletedAt : String?,
    @SerializedName("UserId")
    val UserId : Int,
    @SerializedName("GoodId")
    val goodId : Int?,
    @SerializedName("User")
    val ownerData : OwnerData?
) : java.io.Serializable

