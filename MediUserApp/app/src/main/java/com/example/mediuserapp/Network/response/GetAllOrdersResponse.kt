package com.example.mediuserapp.Network.response

data class GetAllOrdersResponse(
    val message: List<GetAllOrdersResponseMessage>,
    val status: Int
)