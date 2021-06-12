package com.example.socketprogramming.data.response

import com.google.gson.annotations.SerializedName

data class SocketAuctionResponse (
    @SerializedName("good")
    val good : ProductData,
    @SerializedName("auction")
    val auction : List<AuctionData>
) : java.io.Serializable