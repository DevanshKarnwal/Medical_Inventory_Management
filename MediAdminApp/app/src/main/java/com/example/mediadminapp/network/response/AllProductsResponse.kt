package com.example.mediadminapp.network.response

data class AllProductsResponse(
    val message: List<MessageProduct>,
    val status: Int
)