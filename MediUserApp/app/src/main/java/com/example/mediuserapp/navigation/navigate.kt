package com.example.mediuserapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mediuserapp.ui.screens.LoginUi
import com.example.mediuserapp.ui.screens.SignUpUi
import com.example.mediuserapp.viewModel.MyViewModel

@Composable
fun navigate(viewModel: MyViewModel){
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Routes.SignUp){

        composable<Routes.SignUp>{
            SignUpUi(viewModel)
        }

        composable<Routes.Login>{
            LoginUi(viewModel)
        }

    }
}