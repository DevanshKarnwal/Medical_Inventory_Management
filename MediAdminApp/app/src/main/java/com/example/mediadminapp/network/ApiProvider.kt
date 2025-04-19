package com.example.mediadminapp.network

import com.example.mediadminapp.utils.BASE_URL
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.jvm.java

object ApiProvider {
//    Log.d("response","before service call from ApiProvider")

    fun provideApiService() = Retrofit.Builder().baseUrl(BASE_URL)
            .client(OkHttpClient.Builder().build())
            .addConverterFactory(GsonConverterFactory.create()).build().create(ApiServices::class.java)
    }

