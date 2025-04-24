package com.example.mediadminapp.network

import com.example.mediadminapp.network.response.AllProductsResponse
import com.example.mediadminapp.network.response.AllUsersResponse
import com.example.mediadminapp.network.response.ApproveUserResponse
import com.example.mediadminapp.network.response.BlockUserResponse
import com.example.mediadminapp.network.response.CreateProductResponse
import com.example.mediadminapp.network.response.DeleteProductResponse
import com.example.mediadminapp.network.response.DeleteUserResponse
import com.example.mediadminapp.network.response.GetAllOrdersResponse
import com.example.mediadminapp.network.response.OrderApprovalResponse
import com.example.mediadminapp.network.response.SpecificProductResponse
import com.example.mediadminapp.network.response.SpecificUserResponse
import com.example.mediadminapp.network.response.UpdateProductResponse
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.HTTP
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

    @FormUrlEncoded
    @HTTP(method = "DELETE", path = "deleteProduct", hasBody = true)
    suspend fun deleteProduct(
        @Field("product_id") product_id: String
    ): Response<DeleteProductResponse>

    @FormUrlEncoded
    @HTTP(method = "DELETE", path = "deleteUser", hasBody = true)
    suspend fun deleteUser(
        @Field("user_id") user_id: String
    ): Response<DeleteUserResponse>

    @GET("getAllOrderDetails")
    suspend fun getAllOrders() : Response<GetAllOrdersResponse>

    @FormUrlEncoded
    @PATCH("updateOrderApproval")
    suspend fun updateOrderApproval(
        @Field("order_id") order_id: String,
        @Field("isApproved") isApproved: String
    ) : Response<OrderApprovalResponse>

    @FormUrlEncoded
    @PATCH("updateProduct")
    suspend fun updateProductStock(
        @Field("product_id") product_id: String,
        @Field("name") name: String,
        @Field("price") price: String,
        @Field("category") category: String,
        @Field("stock") stock: String,
    ): Response<UpdateProductResponse>

}