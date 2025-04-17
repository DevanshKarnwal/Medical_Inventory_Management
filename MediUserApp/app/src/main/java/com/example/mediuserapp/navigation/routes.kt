package com.example.mediuserapp.navigation

import kotlinx.serialization.Serializable

sealed class Routes{

    @Serializable
    object SignUp : Routes()

    @Serializable
    object Login : Routes()



}