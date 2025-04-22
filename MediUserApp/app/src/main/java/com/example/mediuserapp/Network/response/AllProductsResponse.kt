package com.example.mediuserapp.Network.response

data class AllProductsResponse(
    val message: List<MessageProduct>,
    val status: Int
)