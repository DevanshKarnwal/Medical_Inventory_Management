package com.example.mediadminapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mediadminapp.ui.screens.AllUsersUi
import com.example.mediadminapp.viewModel.myViewModel

@Composable
fun navigate(viewModel: myViewModel){
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Routes.allUsersScreenRoute){

        composable<Routes.allUsersScreenRoute>{
            AllUsersUi(viewModel)
        }

    }
}