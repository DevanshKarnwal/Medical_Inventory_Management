package com.example.mediuserapp.Repo

import com.example.mediuserapp.Network.ApiProvider

class Repo {
    suspend fun createUser(
        name: String,
        password: String,
        phoneNumber: String,
        email: String,
        pincode: String,
        address: String
    ) = ApiProvider.provideApiService().createUser(name, password, phoneNumber, email, pincode, address)

    suspend fun loginUser(
        email: String,
        password: String
    ) = ApiProvider.provideApiService().loginUser(email, password)

}