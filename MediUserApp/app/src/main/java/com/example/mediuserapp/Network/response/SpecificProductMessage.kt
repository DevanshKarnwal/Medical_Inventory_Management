package com.example.mediuserapp.Network.response

data class SpecificProductMessage(
    val category: String,
    val id: Int,
    val name: String,
    val price: Double,
    val product_id: String,
    val stock: Int
)