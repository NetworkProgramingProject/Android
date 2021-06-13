package com.example.socketprogramming.network



import com.example.socketprogramming.data.request.AuctionPriceRequest
import com.example.socketprogramming.data.request.LoginRequest
import com.example.socketprogramming.data.request.ProductRequest
import com.example.socketprogramming.data.request.RegisterRequest
import com.example.socketprogramming.data.response.*
import okhttp3.MultipartBody
import okhttp3.Request
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

/**
 * 실제 서비스에서 사용하는 Retrofit2 인터페이스
 *
 */
interface SocketService {

    @POST("auth/join")
    fun postRegister(
        @Body registerRequest: RegisterRequest
    ): Call<RegisterResponse>

    @POST("auth/login")
    fun postLogin(
        @Body loginRequest: LoginRequest
    ) : Call<LoginResponse>

    @Multipart
    @POST("goods")
    fun postRegisterProduct(
        @Header("Content-Type") contentType : String,
        @Part("title") title : String,
        @Part("desc") desc : String,
        @Part("min_price") min_price : Int,
        @Part img : MultipartBody.Part ?
    ) : Call<LoginResponse>


    @GET("goods")
    fun getProductList(
    ) : Call<SocketBaseResponse<List<ProductData>>>


    @POST("goods/{id}/bid")
    fun postAuctionPrice(
        @Path("id") goodsId : Int,
        @Body auctionPriceRequest: AuctionPriceRequest
    ) : Call<LoginResponse>


    @GET("mypage")
    fun getUserData() : Call<SocketBaseResponse<UserResponse>>
}
