package com.example.mediadminapp.navigation

import kotlinx.serialization.Serializable

sealed class Routes {
    @Serializable
    object allUsersScreenRoute : Routes()
}