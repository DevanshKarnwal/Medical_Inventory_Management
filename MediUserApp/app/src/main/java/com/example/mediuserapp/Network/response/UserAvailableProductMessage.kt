package com.example.mediuserapp.Network.response

data class UserAvailableProductMessage(
    val category: String,
    val id: Int,
    val name: String,
    val price: Double,
    val product_id: String,
    val stock: Int,
    val user_id: String,
    val user_name: String
)