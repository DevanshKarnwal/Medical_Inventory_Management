package com.example.mediuserapp.Network

import com.example.utils.BASE_URL
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiProvider {
        fun provideApiService() = Retrofit.Builder().baseUrl(BASE_URL)
            .client(OkHttpClient.Builder().build())
            .addConverterFactory(GsonConverterFactory.create()).build().create(ApiServices::class.java)
    }

