package com.example.mediadminapp.network

import android.util.Log
import com.example.mediadminapp.network.response.AllUsersResponse
import retrofit2.Response
import retrofit2.http.GET

interface ApiServices {


    @GET("getAllUsers")
    suspend fun getAllUsers(): Response<AllUsersResponse>

}