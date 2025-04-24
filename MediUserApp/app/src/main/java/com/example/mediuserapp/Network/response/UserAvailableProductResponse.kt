package com.example.mediuserapp.Network.response

data class UserAvailableProductResponse(
    val message: List<UserAvailableProductMessage>,
    val status: Int
)