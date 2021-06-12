package com.example.socketprogramming.data.request

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class AuctionPriceRequest (
    @SerializedName("bid")
    val bid : Int
)