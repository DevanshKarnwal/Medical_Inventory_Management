package com.example.mediadminapp.network.response

data class GetAllOrdersResponse(
    val message: List<GetAllOrdersResponseMessage>,
    val status: Int
)