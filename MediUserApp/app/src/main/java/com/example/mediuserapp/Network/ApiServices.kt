package com.example.mediuserapp.Network

import com.example.common.ResultState
import com.example.mediuserapp.Network.response.AllProductsResponse
import com.example.mediuserapp.Network.response.CreateOrderResponse
import com.example.mediuserapp.Network.response.CreateUserResponse
import com.example.mediuserapp.Network.response.GetAllOrdersResponse
import com.example.mediuserapp.Network.response.GetSpecificAvailableProduct
import com.example.mediuserapp.Network.response.LoginUserResponse
import com.example.mediuserapp.Network.response.SpecificProductResponse
import com.example.mediuserapp.Network.response.SpecificUserResponse
import com.example.mediuserapp.Network.response.UserAvailableProductResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiServices {

    @FormUrlEncoded
    @POST("createUser")
    suspend fun createUser(
        @Field("name") name : String,
        @Field("password") password : String,
        @Field("phoneNumber") phoneNumber : String,
        @Field("email") email : String,
        @Field("pincode") pincode : String,
        @Field("address") address : String,
    ) : Response<CreateUserResponse>

    @FormUrlEncoded
    @POST("login")
    suspend fun loginUser(
        @Field("email") email: String,
        @Field("password") password: String,
    ): Response<LoginUserResponse>

    @FormUrlEncoded
    @POST("getSpecificUser")
    suspend fun specificUser(
        @Field("user_id") user_id: String,
    ) : Response<SpecificUserResponse>

    @GET("getAllProducts")
    suspend fun getAllProducts(): Response<AllProductsResponse>

    @FormUrlEncoded
    @POST("getSpecificAvailableProducts")
    suspend fun getSpecificProduct(
        @Field("product_id") product_id: String
    ): Response<GetSpecificAvailableProduct>

    @GET("getAvailableProducts")
    suspend fun getUserAvailableProducts(): Response<UserAvailableProductResponse>

    @FormUrlEncoded
    @POST("createOrderDetails")
    suspend fun createOrderDetails(
        @Field("user_id") user_id: String,
        @Field("product_id") product_id: String,
        @Field("quantity") quantity: String,
        @Field("isApproved") isApproved: String = "0",
        @Field("price") price: String,
        @Field("product_name") product_name: String,
        @Field("user_name") user_name: String,
        @Field("message") message: String,
        @Field("category") category: String,

    ) : Response<CreateOrderResponse>

    @GET("getAllOrderDetails")
    suspend fun getAllOrders() : Response<GetAllOrdersResponse>

}