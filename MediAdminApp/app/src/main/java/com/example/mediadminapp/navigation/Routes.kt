package com.example.mediadminapp.navigation

import kotlinx.serialization.Serializable

sealed class Routes {
    @Serializable
    object homeScreenRoute : Routes()

    @Serializable
    data class userDetailScreenRoute(val userId : String) : Routes()

    @Serializable
    object addProductScreenRoute : Routes()

    @Serializable
    object allProductScreenRoute : Routes()

    @Serializable
    data class specificProductScreenRoute(val productId : String) : Routes()



}