package com.example.mediadminapp.network

import com.example.mediadminapp.network.response.AllProductsResponse
import com.example.mediadminapp.network.response.AllUsersResponse
import com.example.mediadminapp.network.response.ApproveUserResponse
import com.example.mediadminapp.network.response.BlockUserResponse
import com.example.mediadminapp.network.response.CreateProductResponse
import com.example.mediadminapp.network.response.SpecificProductResponse
import com.example.mediadminapp.network.response.SpecificUserResponse
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST

interface ApiServices {


    @GET("getAllUsers")
    suspend fun getAllUsers(): Response<AllUsersResponse>

    @FormUrlEncoded
    @POST("getSpecificUser")
    suspend fun specificUser(
        @Field("user_id") user_id: String,
    ): Response<SpecificUserResponse>

    @FormUrlEncoded
    @PATCH("blockUser")
    suspend fun blockUser(
        @Field("user_id") user_id: String,
        @Field("block") block: String
    ): Response<BlockUserResponse>

    @FormUrlEncoded
    @PATCH("approveUser")
    suspend fun approveUser(
        @Field("user_id") user_id: String,
        @Field("approve") approve: String
    ): Response<ApproveUserResponse>

    @FormUrlEncoded
    @POST("createProduct")
    suspend fun createProduct(
        @Field("name") name: String,
        @Field("price") price: String,
        @Field("category") category: String,
        @Field("stock") stock: String,
    ): Response<CreateProductResponse>

    @GET("getAllProducts")
    suspend fun getAllProducts(): Response<AllProductsResponse>

    @FormUrlEncoded
    @POST("getSpecificProduct")
    suspend fun getSpecificProduct(
        @Field("product_id") product_id: String
    ): Response<SpecificProductResponse>


}