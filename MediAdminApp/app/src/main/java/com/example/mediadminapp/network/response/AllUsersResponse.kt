package com.example.mediadminapp.network.response

data class AllUsersResponse(
    val message: List<Message>,
    val status: Int
)