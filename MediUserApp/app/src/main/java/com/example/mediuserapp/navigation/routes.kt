package com.example.mediuserapp.navigation

import com.example.mediuserapp.Network.response.Message
import kotlinx.serialization.Serializable

sealed class Routes{

    @Serializable
    object SignUp : Routes()

    @Serializable
    object Login : Routes()

    @Serializable
    data class WaitingScreen(val userId : String) : Routes()

    @Serializable
    data class HomeScreen(val userId : String) : Routes()

}