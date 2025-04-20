package com.example.mediadminapp.network.response

data class MessageProduct(
    val category: String,
    val id: Int,
    val name: String,
    val price: Double,
    val product_id: String,
    val stock: Int
)