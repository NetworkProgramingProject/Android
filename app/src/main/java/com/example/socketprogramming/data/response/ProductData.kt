package com.example.socketprogramming.data.response

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable
import java.util.*

@Serializable
data class ProductData(
    @SerializedName("id")
    val id : Int,
    @SerializedName("title")
    val title : String,
    @SerializedName("img")
    val image : String?,
    @SerializedName("min_price")
    val minPrice : Int,
    @SerializedName("desc")
    val desc : String,
    @SerializedName("deadline")
    val deadline : String?,
    @SerializedName("createdAt")
    val createAt : String,
    @SerializedName("updatedAt")
    val updatedAt : String,
    @SerializedName("deletedAt")
    val deletedAt : String,
    @SerializedName("OwnerId")
    val ownerId : Int,
    @SerializedName("SoldId")
    val soldId : Int?,
    @SerializedName("Owner")
    val ownerData : OwnerData?
) : java.io.Serializable

