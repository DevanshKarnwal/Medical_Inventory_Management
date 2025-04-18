package com.example.mediuserapp.Network

import com.example.common.ResultState
import com.example.mediuserapp.Network.response.CreateUserResponse
import com.example.mediuserapp.Network.response.LoginUserResponse
import com.example.mediuserapp.Network.response.SpecificUserResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
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

}