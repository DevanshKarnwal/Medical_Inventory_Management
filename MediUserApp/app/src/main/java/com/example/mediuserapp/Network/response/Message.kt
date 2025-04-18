package com.example.mediuserapp.Network.response

data class Message(
    val address: String,
    val block: Int,
    val date_of_account_creation: String,
    val email: String,
    val id: Int,
    val isApproved: Int,
    val name: String,
    val password: String,
    val phoneNumber: String,
    val pincode: String,
    val user_id: String
)