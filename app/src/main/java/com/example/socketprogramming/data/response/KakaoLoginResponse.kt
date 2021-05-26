package com.example.perfumeproject.data.response

import com.google.gson.annotations.SerializedName

data class KakaoLoginResponse(
        @SerializedName("token")
        val token : String
)
